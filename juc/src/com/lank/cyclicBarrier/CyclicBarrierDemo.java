package com.lank.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lank
 * @since 2020/11/4 22:38
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
            System.out.println("五个小伙伴都到齐了，大家统一出发!!!!");
        });
        for (int i=0;i<5;i++){
            new Thread(new Task(i,cyclicBarrier)).start();
        }

    }

    static class Task implements Runnable{
        private Integer id ;
        private CyclicBarrier cyclicBarrier;

        public Task(Integer id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+id+"前往集合地点");
            try {
                Thread.sleep((long) (Math.random()*1000));
                System.out.println("线程"+id+"到达集合点");
                cyclicBarrier.await();
                System.out.println("线程"+id+"开始出发");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
