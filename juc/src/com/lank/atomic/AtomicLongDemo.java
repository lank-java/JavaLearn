package com.lank.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lank
 * @since 2020/10/23 16:55
 * 演示高并发下，LongAdder比AtomicLong性能好
 */
public class AtomicLongDemo {

    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(0);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Runnable runnable = ()->{
          for (int i=0;i<10000;i++){
              atomicLong.getAndIncrement();
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
        System.out.println(atomicLong.get());
        System.out.println("atomicLong耗时："+ (end-start));

    }
}
