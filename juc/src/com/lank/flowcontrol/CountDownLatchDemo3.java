package com.lank.flowcontrol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lank
 * @since 2020/11/3 21:49
 * 多等一，五个运动员等待发令枪，最后裁判等到所有运动员跑完后比赛结束
 */
public class CountDownLatchDemo3 {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i=1;i<=5;i++){
            final int no = i;
            Runnable  runnable = () -> {
                try {
                    System.out.println("第"+no+"号运动员准备就绪");
                    countDownLatch.await();
                    System.out.println("第"+no+"号运动员起飞");
                    Thread.sleep((long) (Math.random()*1000));
                    System.out.println("第"+no+"号运动员到达终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    end.countDown();
                }
            };
            executorService.submit(runnable);
        }
        Thread.sleep(5000);
        System.out.println("比赛开始");
        countDownLatch.countDown();
        end.await();
        System.out.println("比赛结束");
    }
}
