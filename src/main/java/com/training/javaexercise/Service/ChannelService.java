package com.training.javaexercise.Service;

import com.training.javaexercise.Model.Channel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "channel-client-service")
public interface ChannelService {

    @GetMapping
    public Channel getChannel();
}
