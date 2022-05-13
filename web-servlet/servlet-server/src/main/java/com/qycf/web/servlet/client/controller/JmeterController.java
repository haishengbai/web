package com.qycf.web.servlet.client.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class JmeterController {


    @PostMapping("/jmeter")
    public ResponseEntity<Map> jmeter(@RequestBody Map<String,String> params) {
        log.info("code = {}", params.get("code"));
        Map<String, String> resp = Maps.newHashMap();
        resp.put("code", "200");
        resp.put("msg", params.get("code"));
        return ResponseEntity.ok(resp);
    }

}
