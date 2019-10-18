package com.sun.control.web.common.logQueue;


import com.sun.control.web.common.entity.SystemLog;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志的队列
 */
public class LogQueue {
    //队列大小
    public static final int QUEUE_MAX_SIZE    = 100;


    /**
     * 消息入队
     * @param systemLog
     * @return
     */
    public static void push(SystemLog systemLog) throws Exception {
        //队列已满时,会阻塞队列,直到未满
        blockingQueue.put(systemLog);
    }
    /**
     * 消息出队
     * @return
     */
    public static SystemLog poll() {
        SystemLog result = null;
        try {
            //队列为空时会阻塞队列,直到不是空
            result = blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取队列大小
     * @return
     */
    public static int size() {
        return blockingQueue.size();
    }

    /**
     *
     设置核心池大小,也就是能允许同时运行的线程数,corePoolSize 表示允许线程池中允许同时运行的最大线程数。
     */
    static int corePoolSize = 100;
    /**
     表示线程没有任务时最多保持多久然后停止。默认情况下，只有线程池中线程数大于corePoolSize 时，keepAliveTime 才会起作用。
     换句话说，当线程池中的线程数大于corePoolSize，并且一个线程空闲时间达到了keepAliveTime，那么就是shutdown。
     *
     */
    static long keepActiveTime = 200;
    /**
     * 线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。
     * 值得注意的是，如果使用了无界的任务队列这个参数就没用了。
     */
    static int maximumPoolSize = 300;


    static TimeUnit timeUnit = TimeUnit.SECONDS;


    /**创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
     *
     */

    public static ThreadPoolExecutor executor = null;

    /**
     初始化阻塞队列
     *
     */
    public static BlockingQueue<SystemLog> blockingQueue = null;
    static{
        /**
         * 这是日志队列,用来实际操作的
         * LinkedBlockingQueue（阻塞队列）
         */
        blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);
        /**
         *queue:workQueue必须是BlockingQueue阻塞队列。当线程池中的线程数超过它的corePoolSize的时候，线程会进入阻塞队列进行阻塞等待。通过workQueue，线程池实现了阻塞功能
         */
        /**
         * 这个只是线程池的阻塞队列
         */
        executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepActiveTime,timeUnit,new LinkedBlockingQueue<Runnable>(100));
    }


    /**
     * 初始化线程池
     * @return
     */
    /*public static ThreadPoolExecutor createThreadPool(){
        if(executor == null){
        executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepActiveTime,timeUnit,blockingQueue);
        }
        return executor;
    }*/

}

