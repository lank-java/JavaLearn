package com.lank.threadlocal;

/**
 * @author lank
 * @since 2020/10/21 21:41
 */
public class ThreadLocalMethod {

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        });
        ThreadLocal<Long> threadLocal = new ThreadLocal<>();
        Long aLong = threadLocal.get();
        threadLocal.set(11L);
        threadLocal.remove();
    }
}
