package com.training.javaexercise.Service.Implementation;

import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class HystrixClientFallbackImpl implements ChannelService {

    @Override
    public Channel getChannel() {
        return null;
    }

}
