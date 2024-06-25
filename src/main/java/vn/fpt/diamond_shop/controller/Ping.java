package vn.fpt.diamond_shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class Ping {
    @GetMapping("/")
    public String ping() {
        return "Pong";
    }
}
