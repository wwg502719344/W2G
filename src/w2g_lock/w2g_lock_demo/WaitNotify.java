package w2g_lock.w2g_lock_demo;

import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/8/5 0005.
 * java 通知等待机制
 * 基本原理:一个线程修改了对象的值，另一个线程感知到了，然后进行相应的业务操作
 * 简单的生产者和消费者的模式
 * 代码：
 * while(value!=desire){
 *     xxx.wait();
 * }
 * doSomething();
 * 在while中设置不满足条件的条件，通过wait进入WAITING状态，生产者更新value值的
 * 时候，就会调用notify唤醒该线程
 *
 */
public class WaitNotify {
    static boolean flag=true;
    static Object lock=new Object();

    public static void main(String[] args)throws Exception{
        Thread waitThread=new Thread(new Wait(),"waitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread=new Thread(new Notify(),"notifyThread");
        notifyThread.start();

    }

    /**
     * 在特定条件下将线程变为等待状态，当不满足等待条件时
     * 执行相关代码
     */
    static class Wait implements Runnable {
        @Override
        public void run() {
            //加锁
            synchronized (lock){
                //当条件不满足的时候进入WAITING状态，并释放锁
                while(flag){
                    System.out.println("flag is true");
                    try {
                        //将当前线程从RUNNING变为WAITING状态
                        //从wait方法返回的前提是获得了调用对象的锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //条件满足
                System.out.println("do something");
            }
        }
    }

    /**
     * 通知模式，将线程从等待队列移动到通知队列，并通知进行等待的线程
     */
    static class Notify implements Runnable {
        @Override
        public void run() {
            //加锁
            synchronized (lock){
                /**
                 *notify方法调用后，等待线程不会从wait方法返回，该方法只是把等待线程从等待队列移动到
                 *同步队列当中去，被移动的线程由WATIONG变成BLOCKED
                 */
                lock.notify();
                System.out.println("flag is false now");
                flag=false;
            }
        }
    }
}
