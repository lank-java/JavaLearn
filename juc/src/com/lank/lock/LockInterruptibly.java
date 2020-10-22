package com.lank.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/10/22 14:11
 */
public class LockInterruptibly implements Runnable{

    static Lock lock = new ReentrantLock();
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"开始获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName()+"获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"睡眠期间被中断了");
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"获取锁期间被中断了");
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了锁");
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(new LockInterruptibly());
        Thread thread2 = new Thread(new LockInterruptibly());
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // thread1.interrupt();
        thread2.interrupt();
    }
}
