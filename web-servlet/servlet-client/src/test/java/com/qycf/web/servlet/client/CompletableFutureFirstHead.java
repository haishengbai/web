package com.qycf.web.servlet.client;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static org.junit.Assert.*;

public class CompletableFutureFirstHead {

    @Test
    public void completedFutureExample() {
        CompletableFuture completableFuture = CompletableFuture.completedFuture("message");
        assertTrue(completableFuture.isDone());
        assertEquals("message", completableFuture.getNow(null));
    }

    @Test
    public void runAsyncExample() throws InterruptedException {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertFalse(cf.isDone());
        Thread.sleep(5000);
        assertTrue(cf.isDone());

    }

    @Test
    public void thenApplyExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        assertEquals("MESSAGE", cf.getNow(null));
    }

    @Test
    public void thenApplyAsyncExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        });
        assertNull(cf.getNow(null));
        System.out.println("---------------");
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void thenApplyAsyncWithExecutorExample(){
        ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int count = 1;

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "custom-executor-" + count++);
            }
        });

        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        }, executor);

        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());

    }

    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void thenAcceptAsyncExample() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> result.append(s));
        cf.join();
//        Thread.sleep(3000);
        assertTrue("Result was empty", result.length() > 0);
    }


}