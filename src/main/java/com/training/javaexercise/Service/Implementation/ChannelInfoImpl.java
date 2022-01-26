package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final String API_URL = "http://channel-client-service/api/channel/";
    private final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoImpl.class);

    /*
     * NORMAL
     ! REST TEMPLATE WOULD BE DEPRECATED IN THE FUTURE
     */
    @HystrixCommand(
            fallbackMethod = "getFallbackChannel",
            threadPoolKey = "channelPool", //BUCKET POOL
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),      // THREADS SIZE
                    @HystrixProperty(name = "maxQueueSize", value = "10")   // MAX QUEUE BEFORE BUCKET IS OPEN
            }
    )
    public ResponseEntity<Channel> getChannel() {
        Channel dummyChannel = restTemplate.getForObject(API_URL + "hello", Channel.class);
        if (dummyChannel == null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dummyChannel);}
        return ResponseEntity.ok().body(dummyChannel);
    }

    public Channel getFallbackChannel() {
        return new Channel(0L, "Unknown Channel", 404);
    }

    public ResponseEntity<Channel> postChannel(Channel channel) {
        return ResponseEntity.ok(restTemplate.postForObject(API_URL + "post", channel, Channel.class));
    }

    public ResponseEntity<Channel> updateChannel(Channel channel) {
        return ResponseEntity.ok(restTemplate.patchForObject(API_URL + "update", channel, Channel.class));
    }

    public void deleteChannel(String name) {
        restTemplate.delete(API_URL + "post", name, Channel.class);
    }

    /**
     * REACTIVE
     */
    @HystrixCommand(
            fallbackMethod = "getFallbackChannelReactive",
            threadPoolKey = "channelPool", //BUCKET POOL
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),      // THREADS SIZE
                    @HystrixProperty(name = "maxQueueSize", value = "10")   // MAX QUEUE BEFORE BUCKET IS OPEN
            }
    )
    public Mono<Channel> getChannelByIdReactive() {
        Mono<Channel> dummyChannel = webClientBuilder.build()
                .get()
                .uri(API_URL + "hello", Channel.class)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(Channel.class).flatMap(body  -> Mono.error(new ServerErrorException(body.toString())))
                )
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(Channel.class).flatMap(body  -> Mono.error(new ServerErrorException(body.toString())))
                )
                .bodyToMono(Channel.class);
        return dummyChannel;
    }

    public Mono<Channel> postChannelReactive(Channel channel) {
        Mono<Channel> dummyChannel = webClientBuilder.build()
                .post()
                .uri(API_URL + "post")
                .body(Mono.just(channel), Channel.class) //THIS IS THE POST BODY TO SEND
                .retrieve()
                .bodyToMono(Channel.class);
        return dummyChannel;
    }

    public Mono<Channel> putChannelReactive(Channel channel) {
        Mono<Channel> dummyChannel = webClientBuilder.build()
                .put()
                .uri(API_URL + "post")
                .body(Mono.just(channel), Channel.class) //THIS IS THE POST BODY TO SEND
                .retrieve()
                .bodyToMono(Channel.class);
        return dummyChannel;
    }

    public Mono<Channel> deleteChannelReactive(String channelName) {
        Mono<Channel> dummyChannel = webClientBuilder.build()
                .delete()
                .uri(API_URL + "post/" + channelName)
                .retrieve()
                .bodyToMono(Channel.class);
        return dummyChannel;
    }

    public Mono<Channel> getFallbackChannelReactive() {
        return Mono.just(new Channel(0L, "Unknown Channel", 404));
    }





}
