package com.qycf.web.servlet.client.controller;

import com.qycf.web.servlet.common.utils.HttpClientUtils;
import com.qycf.web.servlet.common.utils.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class HttpRequestController {

    @GetMapping("/mock/broken/pipe")
    public String requestMockBrokenPipe() {


        return "request mock broken pipe finish";
    }

}
