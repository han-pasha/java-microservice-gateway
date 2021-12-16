package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.javaexercise.Model.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChannelInfoImpl {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getFallbackChannel",
            threadPoolKey = "channelPool", //BUCKET POOL
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),      // THREADS SIZE
                    @HystrixProperty(name = "maxQueueSize", value = "10")   // MAX QUEUE BEFORE BUCKET IS OPEN
            }
    )
    public Channel getChannel() {
        return restTemplate.getForObject("http://ChannelClient/api/channel/hello", Channel.class);
    }

    public Channel getFallbackChannel() {
        return new Channel(0L, "Unknown Channel", 404);
    }
}
