package com.training.javaexercise.Controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Model.Television;
import com.training.javaexercise.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/mvc/")
public class TelevisionController {

    @Autowired
    private NewsService newsService;

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
}
