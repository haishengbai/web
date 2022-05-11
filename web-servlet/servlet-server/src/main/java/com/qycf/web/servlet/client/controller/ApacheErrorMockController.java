package com.qycf.web.servlet.client.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/mock")
public class ApacheErrorMockController {


    @GetMapping("/brokenPipe")
    public ResponseEntity<Map<String, Object>> mockBrokenPipe() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            log.info("sleep {} s", i);
        }
        Map<String, Object> res = Maps.newHashMap();
        res.put("code", 0);
        res.put("msg", "finish http connect");
        return ResponseEntity.ok(res);
    }


}
