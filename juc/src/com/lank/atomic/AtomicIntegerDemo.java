package com.lank.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lank
 * @since 2020/10/23 10:52
 * @desc AtomicInteger 基于CAS
 */
public class AtomicIntegerDemo {

    private static AtomicInteger atomicCount = new AtomicInteger();
    private static int count = 0;

    public static  void incrementBasic(){
        count++;
    }

    public static void incrementAtomic(){
        atomicCount.incrementAndGet();
        atomicCount.getAndIncrement();

    }

    public static void main(String[] args) {
        Runnable r = ()->{
            for (int i=0;i<10000;i++){
                incrementAtomic();
                incrementBasic();
            }
        };
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("原子相加结果："+atomicCount.get());
        System.out.println("普通相加结果："+count);
    }
}
