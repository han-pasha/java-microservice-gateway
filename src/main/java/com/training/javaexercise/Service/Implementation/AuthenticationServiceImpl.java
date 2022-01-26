package com.training.javaexercise.Service.Implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.javaexercise.Model.JsonFormatter;
import com.training.javaexercise.Model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl {

    @Autowired
    private final RestTemplate restTemplate;
    private final String LOGIN_URI = "http://localhost:8082/login";

    HttpHeaders headers = new HttpHeaders();
    @HystrixCommand(fallbackMethod = "getFallbackBroadcast",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            })
    public ResponseEntity<JsonFormatter> userLogin(String username, String password) {
        log.info("Username {}, Password {} tried to login", username, password);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",username);
        map.add("password",password);
        HttpEntity<MultiValueMap<String, String>> login =  new HttpEntity<>(map, headers);
        ResponseEntity<JsonFormatter> response = restTemplate.exchange(
            LOGIN_URI, 
            HttpMethod.POST, 
            login, 
            JsonFormatter.class);
        return response;
        // return restTemplate.postForObject(LOGIN_URI, login, JsonFormatter.class); 
    }
}
