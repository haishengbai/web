package com.qycf.web.reactive.server.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/mock")
public class ApacheErrorMockController {

    @GetMapping("/brokenPipe")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> mockBrokenPipe() throws InterruptedException {

        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("sleep " + i + "s");
            }
            Map<String, Object> res = Maps.newHashMap();
            res.put("code", 0);
            res.put("msg", "finish http connect");


            return ResponseEntity.ok(res);
        });
        return completableFuture;
    }


}
