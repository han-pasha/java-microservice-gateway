package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChannelInfoImpl {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

//    @HystrixCommand(
//            fallbackMethod = "getFallbackChannel",
//            threadPoolKey = "channelPool", //BUCKET POOL
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "20"),      // THREADS SIZE
//                    @HystrixProperty(name = "maxQueueSize", value = "10")   // MAX QUEUE BEFORE BUCKET IS OPEN
//            }
//    )
    public Channel getChannel() {
        return restTemplate.getForObject("http://channel-client-service/api/channel/hello", Channel.class);
    }

    @HystrixCommand(
            fallbackMethod = "getFallbackChannel",
            threadPoolKey = "channelPool", //BUCKET POOL
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),      // THREADS SIZE
                    @HystrixProperty(name = "maxQueueSize", value = "10")   // MAX QUEUE BEFORE BUCKET IS OPEN
            }
    )
    public Mono<Channel> getChannelByIdReactive() {
        Channel channel = webClientBuilder.build()
                .get()
                .uri("http://ChannelClient/api/channel/hello", Channel.class)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(Channel.class).flatMap(body  -> Mono.error(new ServerErrorException(body.toString())))
                )
                .bodyToMono(Channel.class)
                .block();
        return channel != null ? Mono.just(channel) : Mono.just(getFallbackChannel());
    }

    public Channel getFallbackChannel() {
        return new Channel(0L, "Unknown Channel", 404);
    }
}
