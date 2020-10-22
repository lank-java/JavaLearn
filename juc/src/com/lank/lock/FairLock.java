package com.lank.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/10/22 15:48
 * 演示公平锁和非公平锁
 */
public class FairLock {

    private static ReentrantLock lock = new ReentrantLock(false);

    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        for (int i=0;i<10;i++){
            service.execute(()->{
                lock.lock();
                try {
                    int time = new Random().nextInt(10)+1;
                    System.out.println(Thread.currentThread().getName()+"进行第一次打印需要："+time+"秒");
                    try {
                        Thread.sleep(time * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

                lock.lock();
                try {
                    int time = new Random().nextInt(10)+1;
                    System.out.println(Thread.currentThread().getName()+"进行第二次打印需要："+time+"秒");
                    try {
                        Thread.sleep(time * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            });
        }
        service.shutdown();
    }


}
