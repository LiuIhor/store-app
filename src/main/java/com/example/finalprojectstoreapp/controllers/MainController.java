package com.example.finalprojectstoreapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    /**
     * Endpoint to open swagger doc
     *
     * @return Redirect to swagger doc
     */
    @RequestMapping("/")
    public String doc() {
        log.info("Redirect to swagger-ui/index.html#");
        return "redirect:swagger-ui/index.html#";
    }
}