package com.sun;

public class TestThread {
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        thread1.run();
        //thread1.start();启动t3线程  实现Thread（）
        //thread1.join();阻塞主线程，执行完t3再返回
    }
}
class Thread1 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread2 thread2 = new Thread2();
        thread2.run();
        System.out.println(1111);
    }
}
class Thread2 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread3 thread3 = new Thread3();
        thread3.run();
        System.out.println(2222);
    }
}

class Thread3 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(3333);
    }
}
