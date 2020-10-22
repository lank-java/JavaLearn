package com.lank.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lank
 * @since 2020/10/22 17:27
 * 自旋锁
 */
public class SpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<>();

    private void lock(){
        Thread thread = Thread.currentThread();
        while (!sign.compareAndSet(null, thread)){
            System.out.println(Thread.currentThread().getName()+"获取锁失败，重新尝试");
        }
    }

    private void unlock(){
        Thread thread = Thread.currentThread();
        sign.compareAndSet(thread,null);
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread() + "尝试获取锁");
            spinLock.lock();
            System.out.println(Thread.currentThread() + "成功获取到了锁");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

    }
}
