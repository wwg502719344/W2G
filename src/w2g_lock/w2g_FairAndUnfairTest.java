package w2g_lock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by W2G on 2018/5/4
 * 公平锁与非公平锁测试
 */
public class w2g_FairAndUnfairTest {

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock= new ReentrantLock2(false);
    private static CountDownLatch start;

    @Test
    public void fair(){
        testLock(fairLock);
    }

    @Test
    public void unfair(){
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        //构造一个同时执行的构造函数，当传值为0时同时执行
        start=new CountDownLatch(1);
        for (int i=0;i<100;i++){
            Thread thread=new Job(lock);
            thread.setName(""+i);
            thread.start();
        }
        //开始执行
        start.countDown();
    }

    private class Job extends Thread {

        private Lock lock;

        public Job(Lock lock) {
            this.lock=lock;
        }

        @Override
        public void run() {
            try{
                //使当前线程等待，直到latch(锁存器)值为0
                start.await();
            }catch (InterruptedException e){
            }

            for (int i=0;i<2;i++){
                lock.lock();
                try{
                    System.out.println("Lock by ["+getName()+"],waiting by "+((ReentrantLock2)lock).getQueuedThreads());
                }finally {
                    lock.unlock();
                }
            }

        }

        public String toString() {
            return getName();
        }
    }

    //内部类继承重入锁
    private static class ReentrantLock2 extends ReentrantLock {
        //调用父类的构造方法，判断是调用公平还是非公平
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads(){
            //getQueuedThreads返回包含当前线程的集合(可能正在等待获取锁)
            List<Thread> arrayList=new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
