package com.training.javaexercise.Controller;

import com.training.javaexercise.Model.Broadcast;
import com.training.javaexercise.Model.Channel;
import com.training.javaexercise.Model.JsonFormatter;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import com.training.javaexercise.Service.ChannelService;
import com.training.javaexercise.Service.Implementation.BroadcastInfoImpl;
import com.training.javaexercise.Service.Implementation.ChannelInfoImpl;
import com.training.javaexercise.Service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/api/mvc/")
@AllArgsConstructor
@Slf4j
public class TelevisionController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ChannelInfoImpl channelServiceRestTemplates;
    @Autowired
    private BroadcastInfoImpl broadcastService;
    @Autowired
    private final ChannelService channelServiceOpenFeign;

    @GetMapping("welcome")
    public String greetWelcome() {
        return "welcome"; // This is from jsp
    }

    @GetMapping("greetings")
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok().body("Hello World!");
    }

//    @GetMapping("welcome/{name}")
    @RequestMapping(value="welcome/{name}", method = RequestMethod.GET)
    public String greetWelcomeFromUrl(@PathVariable String name, Model model) {
        model.addAttribute("urlGreeting", name);
        return "welcome";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        return "/user/login";
    }

    @PostMapping("testing/login-with-service")
    public ResponseEntity<JsonFormatter> loginToService(@RequestBody JsonFormatter user) {
        log.info("This method called because of login");
        if (user == null) {
            return null;
        }
        String username = user.getToken();
        String password = user.getRefrestToken();
//        return authenticationService.userLogin(username, password);
        return ResponseEntity.ok().body(new JsonFormatter("Yow", "Error mulu anjing"));
    }

    @GetMapping(value = "param/welcome/{name}", params = "user")
    public String greetWelcomeWithParam(@PathVariable String name,
                                        @RequestParam String user,
                                        Model model) {
        model.addAttribute("urlName", name);
        model.addAttribute("paramUser", user);
        return "welcome";
    }

    @GetMapping("microservice/testing/{id}")
    public ResponseEntity<Television> getTelevision(@PathVariable Long id) {
        return ResponseEntity.ok().body(newsService.getTvInfo(id));
    }

    @PostMapping("crud/news/post")
    public ResponseEntity<News> postNews(@RequestBody News news) {
        return ResponseEntity.ok().body(newsService.createNews(news));
    }

    @PutMapping("crud/news/update")
    public ResponseEntity<News> updateNews(@RequestBody News news) {
        return ResponseEntity.ok().body(newsService.createNews(news));
    }

    /*
     * THIS IS FROM MICROSERVICE
     * @return dont know bro
     */
    @GetMapping("channel/get")
    public ResponseEntity<Channel> getChannel() {
        return channelServiceRestTemplates.getChannel();
    }

    @PostMapping("channel/post")
    public ResponseEntity<Channel> postChannel(@RequestBody Channel channel) {
        return channelServiceRestTemplates.postChannel(channel);
    }

    @PutMapping("channel/post")
    public ResponseEntity<Channel> updateChannel(@RequestBody Channel channel) {
        return channelServiceRestTemplates.getChannel();
    }

    @DeleteMapping("channel/delete/{channelName}")
    public ResponseEntity<Channel> deleteChannel(@PathVariable String channelName) {
        return channelServiceRestTemplates.getChannel();
    }

    @GetMapping("channel/get-reactive")
    public ResponseEntity<Mono<Channel>> getChannelReactive() {
        return ResponseEntity.ok().body(channelServiceRestTemplates.getChannelByIdReactive());
    }

    @GetMapping("channel-feign/get")
    public ResponseEntity<Channel> getChannelFeign() {
        return ResponseEntity.ok().body(channelServiceOpenFeign.getChannel());
    }

    /*
     * BROADCAST
     */
    @GetMapping("broadcast/get")
    public ResponseEntity<Broadcast> getBroadcast() {
        return ResponseEntity.ok().body(broadcastService.getBroadcast(1L));
    }

}
