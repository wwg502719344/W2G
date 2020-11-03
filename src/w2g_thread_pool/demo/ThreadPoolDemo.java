package w2g_thread_pool.demo;


import java.util.concurrent.*;

/**
 * Created by W2G on 2018/11/5.
 * 创建线程池的几种方式
 * 核心思想:
 * 1:创建一个线程工厂
 * 2:通过ThreadPoolExecutor构造方法创建一个线程池
 * 3:创建一个任务类交给线程池对象进行执行，executorService.execute(myTask);
 */
public class ThreadPoolDemo {

    /**
     * 方式一
     */
    /*
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
*/

    /**
     * 方式二
     */
    /*ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
*/

    /**
     * 完整示例
     */
    /*public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread name:"
                    + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(myTask);
        }
        executorService.shutdown();
    }*/





}

