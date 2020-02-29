package com.wuhulala.sda.reader.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExecutorUtils {

    public static ThreadPoolExecutor newThreadPoolExecutor(String prefix, int core, int max) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(core, max, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1000), new NamedThreadExecutorFactory(prefix));
        return poolExecutor;
    }

    public static class NamedThreadExecutorFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger();

        private String prefix;

        public NamedThreadExecutorFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName(prefix + count.incrementAndGet());
            return thread;
        }
    }
}
