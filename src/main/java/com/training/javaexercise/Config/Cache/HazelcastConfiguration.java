package com.training.javaexercise.Config.Cache;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HazelcastConfiguration {

//    @Bean
    public Config hazelCastConfig(){
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("television")
                                .setTimeToLiveSeconds(20));
    }
}
