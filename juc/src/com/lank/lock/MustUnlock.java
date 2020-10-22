package com.lank.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/10/22 10:49
 * lock不会像synchronized一样异常自动释放锁，所以需要在finally中释放锁
 */
public class MustUnlock {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName());
        }finally {
            lock.unlock();
        }
    }
}

