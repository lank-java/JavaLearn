package com.lank.flowcontrol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lank
 * @since 2020/11/3 21:32
 * countDownLatch 案例一：倒计数结束前，一直处于等待状态，直到倒计数结束
 * 一等多
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i=1;i<=5;i++){
            final int no = i;
            Runnable  runnable = () -> {
                try {
                    Thread.sleep((long) (Math.random()*10000));
                    System.out.println("第"+no+"号工人完成了检查");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            };
            executorService.submit(runnable);
        }
        System.out.println("等待所有的工人开始检查!!!!!");
        countDownLatch.await();
        System.out.println("所有的工人都检查完了啦");
    }
}
