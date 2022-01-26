package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.javaexercise.Model.Broadcast;
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
public class BroadcastInfoImpl {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallbackBroadcast",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
        })
    public Broadcast getBroadcast(Long id) {
        return restTemplate.getForObject("http://broadcast-client-service/api/broadcast/get/"+ id,Broadcast.class);
    }

    @HystrixCommand(fallbackMethod = "getFallbackBroadcast",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            })
    public Mono<Broadcast> getBroadcastByIdReactive(Long id) {
        Broadcast broadcast = webClientBuilder.build()
                .get() // REQUEST HEADER
                .uri("http://BroadcastClient/api/broadcast/get/"+ id)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(Broadcast.class).flatMap(body -> Mono.error(new ServerErrorException(body.toString())))
                )
                .bodyToMono(Broadcast.class)
                .block();
        // Hystrix still used tho
        return broadcast != null ? Mono.just(broadcast) : Mono.just(getFallbackBroadcast(id));
    }

    public Broadcast getFallbackBroadcast(Long id) {
        return new Broadcast(id,"Unknown Broadcast", HttpStatus.NO_CONTENT.toString());
    }
}
