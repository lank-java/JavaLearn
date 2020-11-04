package com.lank.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lank
 * @since 2020/10/23 17:24
 *
 * 演示高并发下，LongAdder比AtomicLong性能好
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Runnable runnable = ()->{
            for (int i=0;i<10000;i++){
                longAdder.increment();
            }
        };
        long start = System.currentTimeMillis();
        for (int i=0;i<10000;i++){
            executorService.submit(runnable);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){

        }
        long end = System.currentTimeMillis();
        System.out.println(longAdder.sum());
        System.out.println("longAdder耗时："+ (end-start));

    }
}
