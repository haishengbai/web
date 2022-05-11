package com.qycf.web.servlet.client.controller;

import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;

@Controller
public class CompletableFutureController {



    public void demoCompletableFuture() {
        CompletableFuture cf = CompletableFuture.completedFuture("message");

    }

}
