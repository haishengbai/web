package com.qycf.web.netty.spring.netty.utils;

import java.util.concurrent.*;

public class ExecutorUtils {

    public static ExecutorService executorService = new ThreadPoolExecutor(10,
            100, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));

    public static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(8);

}
