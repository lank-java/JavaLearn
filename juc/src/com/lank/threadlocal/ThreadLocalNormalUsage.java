package com.lank.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lank
 * @since 2020/10/21 10:57
 */
public class ThreadLocalNormalUsage {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = ThreadLocal.withInitial(
            ()-> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    );

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        ThreadLocalNormalUsage threadLocalNormalUsage = new ThreadLocalNormalUsage();

        for (int i=0;i<1000;i++){
            int finalI = i;
            executorService.execute(()->{
                System.out.println(threadLocalNormalUsage.date(finalI));
            });
        }

        executorService.shutdown();
    }

    public String date(int seconds){
        Date date = new Date(seconds * 1000);
        String format = null;
        /*try {
            lock.lock();
            format = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }*/
        format = simpleDateFormatThreadLocal.get().format(date);

        return format;
    }
}
