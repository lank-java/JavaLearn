package com.lank.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author lank
 * @since 2020/10/23 11:18
 * AtomicIntegerFieldUpdater 使用注意点，变量需volatile修饰，不能static修饰
 *  
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable{

    private static Candidate tom;
    private static Candidate jack;

    public static AtomicIntegerFieldUpdater<Candidate> atomicIntegerFieldUpdater  = AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");

    @Override
    public void run() {
        for (int i=0;i<10000;i++){
            tom.score++;
            atomicIntegerFieldUpdater.incrementAndGet(jack);
        }
    }

    static class Candidate{
         volatile int score;
    }

    public static void main(String[] args) {
        tom = new Candidate();
        jack = new Candidate();

        AtomicIntegerFieldUpdaterDemo atomicIntegerFieldUpdaterDemo = new AtomicIntegerFieldUpdaterDemo();

        Thread thread1 = new Thread(atomicIntegerFieldUpdaterDemo);
        Thread thread2 = new Thread(atomicIntegerFieldUpdaterDemo);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("升级前："+tom.score);
        System.out.println("升级后："+jack.score);
    }



}
