package com.lank.lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lank
 * @since 2020/10/22 16:22
 */
public class ReadWriteLockDemo {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(ReadWriteLockDemo::read);
        executorService.execute(ReadWriteLockDemo::read);
        executorService.execute(ReadWriteLockDemo::write);
        executorService.execute(ReadWriteLockDemo::write);
        executorService.execute(ReadWriteLockDemo::write);
        executorService.execute(ReadWriteLockDemo::write);

        executorService.execute(ReadWriteLockDemo::write);

        executorService.shutdown();

    }

    /**
     * 读操作
     */
    private static void read(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"获取到读锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            System.out.println(Thread.currentThread().getName()+"释放读锁");
            readLock.unlock();
        }
    }

    /**
     * 写操作
     */
    private static  void write(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"获取到写锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            System.out.println(Thread.currentThread().getName()+"释放写锁");
            writeLock.unlock();
        }
    }
}
