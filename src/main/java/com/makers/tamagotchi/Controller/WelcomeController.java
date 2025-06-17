package com.makers.tamagotchi.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
    @RequestMapping(value = "/home")
    public ModelAndView welcome() {
        return new ModelAndView("/welcome");
    }
}
