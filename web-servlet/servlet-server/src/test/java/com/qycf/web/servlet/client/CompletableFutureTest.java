package com.qycf.web.servlet.client;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    private static final int MS_IN_SECOND = 1000;
    private static final Random RANDOM = new Random();
    @Test
    public void initCompletableFuture() {

        /**
         * init one completableFuture
         * */
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        Assert.isTrue(cf.isDone(), "is not done");
        Assert.isTrue("message".equals(cf.getNow(null)), "is not equals message");

    }

    @Test
    public void runAsyncWithCompletableFuture() {

        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            sleepRandomSeconds(1000);
        });

    }



    public static void sleepRandomSeconds(int bound) {
        sleepSeconds(RANDOM.nextInt(bound));
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * MS_IN_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
