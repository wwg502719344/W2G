package w2g_queue.demo;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/8/20.
 *
 */
public class DelayQueueTest {

    public static void main(String args[]){
        DelayQueue<DelayedElement> delayQueue=new DelayQueue<DelayedElement>();

        producer(delayQueue);//朝生产队列中添加任务事件

        consumer(delayQueue);//从队列中获取任务并执行

        while (true){
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static void producer(DelayQueue<DelayedElement> delayQueue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DelayedElement element = new DelayedElement(1000,"test");
                delayQueue.offer(element);
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("delayQueue size:"+delayQueue.size());
                }
            }
        }).start();
    }

    /**
     * 消费者，从延迟队列中获得数据,进行处理
     * @param delayQueue
     */
    private static void consumer(DelayQueue<DelayedElement> delayQueue) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    DelayedElement element = null;
                    try {
                        element =  delayQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis()+"---"+element);
                }
            }
        }).start();
    }

}
