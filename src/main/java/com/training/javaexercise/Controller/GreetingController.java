package com.training.javaexercise.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class GreetingController {

    //@RequestMapping(value = "welcome", method = RequestMethod.GET)
    @GetMapping("welcome")
    public String greetWelcome() {
        return "welcome"; // This is from jsp
    }

    @GetMapping("welcome/{name}")
    public String greetWelcomeFromUrl(@PathVariable String name, Model model) {
        model.addAttribute("urlGreeting", name);
        return "welcome";
    }

    @GetMapping(value = "param/welcome/{name}", params = "user")
    public String greetWelcomeWithParam(@PathVariable String name,
                                        @RequestParam String user,
                                        Model model) {
        model.addAttribute("urlName", name);
        model.addAttribute("paramUser", user);
        return "welcome";
    }
}
