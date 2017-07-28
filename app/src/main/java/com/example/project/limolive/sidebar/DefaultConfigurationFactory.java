package com.example.project.limolive.sidebar;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by litp on 2016/8/12.
 */
public class DefaultConfigurationFactory {
    public DefaultConfigurationFactory() {
    }

    public static Executor createExecutor(int threadPoolSize, int threadPriority) {
        return new ThreadPoolExecutor(threadPoolSize,
                threadPoolSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque(),
                new DefaultThreadFactory(threadPriority, "uil-pool-"));
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            this.namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if(t.isDaemon()) {
                t.setDaemon(false);
            }

            t.setPriority(this.threadPriority);
            return t;
        }
    }
}