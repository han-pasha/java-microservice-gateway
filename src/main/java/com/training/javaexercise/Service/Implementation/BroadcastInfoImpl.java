package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.training.javaexercise.Model.Broadcast;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BroadcastInfoImpl {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackBroadcast")
    public Broadcast getBroadcast(Long id) {
        return restTemplate.getForObject("http://BroadcastClient/api/broadcast/get/"+ id,Broadcast.class);
    }

    public Broadcast getFallbackBroadcast(Long id) {
        return new Broadcast(0L,"Unknown Broadcast", "404");
    }
}
