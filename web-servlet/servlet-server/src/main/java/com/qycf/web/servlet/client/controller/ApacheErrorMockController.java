package com.qycf.web.servlet.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mock")
public class ApacheErrorMockController {


    @GetMapping("/brokenPipe")
    public String mockBrokenPipe() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            log.info("sleep {} s", i);
        }
        return "finish http connect";
    }


}
