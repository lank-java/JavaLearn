package com.lank.condition;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/11/4 22:02
 * condition的基本用法
 */
public class ConditionDemo1 {

    private final ReentrantLock reentrantLock = new ReentrantLock();

    private final Condition condition = reentrantLock.newCondition();

    void method1(){
        try {
            reentrantLock.lock();
            System.out.println("条件不满足，开始wait");
            condition.await();
            System.out.println("条件满足，开始执行");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    void method2(){
        try {
            reentrantLock.lock();
            System.out.println("准备工作完成，开始其他线程");
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionDemo1 conditionDemo1 = new ConditionDemo1();
        Thread thread = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conditionDemo1.method2();
        });
        thread.start();
        conditionDemo1.method1();

    }
}
