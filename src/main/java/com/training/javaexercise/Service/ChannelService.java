package com.training.javaexercise.Service;

import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Service.Implementation.HystrixClientFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "channel-client-service", fallback = HystrixClientFallbackImpl.class)
public interface ChannelService {

    @GetMapping("api/channel/hello")
    Channel getChannel();
}
