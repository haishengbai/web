package com.qycf.web.servlet.client;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
    public void thenApplyAsyncWithExecutorExample() {
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

    @Test
    public void completeExceptionallyExample() {

        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(
                a -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a.toUpperCase();
                });
        CompletableFuture exceptionHandler = cf.handle((s, th) -> {
            return (th != null) ? "message upon cancel" : "";
        });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }


    @Test
    public void cancelExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(a -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a.toUpperCase();
                }
        );
        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    @Test
    public void applyToEitherExample() {
        String original = "Message";
        CompletableFuture cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                });
        CompletableFuture cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toLowerCase();
                }),
                s -> s + " from applyToEither");
        System.out.println(cf2.join().toString());
        assertTrue(cf2.join().toString().endsWith(" from applyToEither"));
    }

    @Test
    public void acceptEitherExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                })
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s.toLowerCase();
                        }),
                        s -> result.append(s).append(" acceptEither"));
        cf.join();
        System.out.println(result);
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }

    @Test
    public void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        System.out.println(result);
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
    }

    @Test
    public void thenCombineExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();

                })
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s.toLowerCase();

                        }),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }


    @Test
    public void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();

                })
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s.toLowerCase();
                        }),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }

    @Test
    public void thenComposeExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(s -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                })
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s.toLowerCase();
                        })
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }


    @Test
    public void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if (th == null) {
//                assertTrue(isUpperCase((String) res));
                System.out.println(res);
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }


    @Test
    public void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> {
//                System.out.println(cf.getNow(null));
//                assertTrue(isUpperCase(cf.getNow(null)));
            });
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> {
                        System.out.println(cf.getNow(null));
//                        assertTrue(isUpperCase(cf.getNow(null)));

                    });
                    result.append("done");
                });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }


}
