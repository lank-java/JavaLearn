package com.lank.condition;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/11/4 22:09
 * condition实现生产者消费者模式
 */
public class ConditionDemo2 {

    private final ReentrantLock lock = new ReentrantLock();

    private final int queueSize = 10;

    private final Queue<Integer> queue = new ArrayDeque<>(queueSize);

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty= lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo2 conditionDemo2 = new ConditionDemo2();
        consumer consumer = conditionDemo2.new consumer();
        producer producer = conditionDemo2.new producer();
        consumer.start();
        producer.start();
    }

    class consumer extends Thread{
        @Override
        public void run() {
            consumer();
        }

        private void consumer(){
            while (true){
                try {
                    lock.lock();
                    while (queue.size()==0){
                        System.out.println("队列为空，等待生产-----");
                        notEmpty.await();
                    }
                    queue.poll();
                    notFull.signal();
                    System.out.println("从队列中取出一个元素,队列剩余"+queue.size()+"元素");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    class producer extends Thread{
        @Override
        public void run() {
            producer();
        }

        private void producer(){
            while (true){
                try {
                    lock.lock();
                    while (queue.size()==queueSize){
                        System.out.println("队列满了，等待消费-----");
                        notFull.await();
                    }
                    queue.offer(1);
                    notEmpty.signal();
                    System.out.println("往队列中添加一个元素,队列还有"+(10-queue.size())+"个空间");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
