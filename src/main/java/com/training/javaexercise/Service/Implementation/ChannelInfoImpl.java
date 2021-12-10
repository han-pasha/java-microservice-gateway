package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "getFallbackChannel")
    public Channel getChannel() {
        return restTemplate.getForObject("http://ChannelClient/api/channel/hello", Channel.class);
    }

    public Channel getFallbackChannel() {
        return new Channel(0L, "Unknown Channel", 404);
    }
}
