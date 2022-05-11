package com.qycf.web.servlet.client.controller;

import com.qycf.web.servlet.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/client")
public class HttpRequestController {

    static Executor executor = Executors.newFixedThreadPool(100000);

    @GetMapping("/mock/broken/pipe")
    public String requestMockBrokenPipe(@RequestParam Integer loop) {

        for (int i = 0; i < loop; i++) {
            int finalI = i;
            executor.execute(() -> {
                Long start = System.currentTimeMillis();
                String result = HttpUtils.getCall("http://localhost:8080/mock/brokenPipe");
                System.out.println("第 " + finalI + "次请求耗时" + (System.currentTimeMillis() - start)/1000 + "s :" + result);
            });

        }
        return "sync execute finish";
    }


    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            executor.execute(() -> {
                String result = HttpUtils.getCall("http://localhost:8080/mock/brokenPipe");
                System.out.println("第 " + finalI + "次请求:" + result);

            });

        }

    }

}
