package com.qycf.web.reactive.web.reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("hello world");
    }

    @PostMapping("/helloMono")
    public Mono<String> helloMono(@RequestBody Mono<String> params) {
        return params.take(Duration.ofMillis(9000));
    }


}
