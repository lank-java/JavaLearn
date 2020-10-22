package com.lank.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/10/22 11:18
 *  用tryLock来避免死锁
 */
public class TryLockDeadLock implements Runnable{

    int flag = 1;

    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadLock r1 = new TryLockDeadLock();
        TryLockDeadLock r2 = new TryLockDeadLock();
        r1.flag = 1;
        r2.flag = 2;
        new Thread(r1).start();
        new Thread(r2).start();
    }


    @Override
    public void run() {
        for (int i=0;i<100;i++){
            if (flag==1){

                try {
                    if (lock1.tryLock(800,TimeUnit.MILLISECONDS)){
                        try {
                            System.out.println("线程1获取到了锁1");
                            Thread.sleep(1000);
                            if (lock2.tryLock(800,TimeUnit.MILLISECONDS)){
                                try{
                                    System.out.println("线程1获取到了锁2");
                                    System.out.println("线程1成功获取到了两把锁");
                                    break;
                                }finally {
                                    System.out.println("线程1释放锁2");
                                    lock2.unlock();
                                }
                            }else {
                                System.out.println("线程1获取锁2失败");
                            }
                        }finally {
                            lock1.unlock();
                            System.out.println("线程1释放锁1");
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    }else {
                        System.out.println("线程1获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    if (lock2.tryLock(800,TimeUnit.MILLISECONDS)){
                        try {
                            System.out.println("线程2获取到了锁2");
                            Thread.sleep(3000);
                            if (lock1.tryLock(800,TimeUnit.MILLISECONDS)){
                                try{
                                    System.out.println("线程2获取到了锁1");
                                    System.out.println("线程2成功获取到了两把锁");
                                    break;
                                }finally {
                                    lock1.unlock();
                                    System.out.println("线程2释放锁1");
                                }
                            }else {
                                System.out.println("线程2获取锁1失败");
                            }
                        }finally {
                            System.out.println("线程2释放锁2");
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    }else {
                        System.out.println("线程2获取锁2失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
