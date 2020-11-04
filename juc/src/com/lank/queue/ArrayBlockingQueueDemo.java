package com.lank.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author lank
 * @since 2020/10/31 17:10
 *
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        new Thread(new Interviewer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }


    static class Interviewer implements Runnable{

        BlockingQueue<String> queue;

        public Interviewer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            //把是个面试者加进去
            System.out.println("开始面试!!!!!");
            for (int i=0;i<10;i++){
                try {
                    queue.put("面试者:"+i);
                    System.out.println("面试安排好了，面试者:"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                queue.put("ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{

        BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                String take;
                while (!"ok".equals(take=queue.take())){
                    System.out.println(take+"面试结束!");
                }
                System.out.println("全部面试完啦啦啦！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
