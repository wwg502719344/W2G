package w2g_thread_pool.pool_run_demo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/9/27 0027.
 * 基本业务类，该类是线程最后的实现类，实现了Runnable接口，run方法就是线程最后的实现类
 */
public class Task implements Runnable {

    private Date initDate;
    private String name;

    public Task(String name) {
        initDate = new Date();
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Task %s: Created on %s\n",
                Thread.currentThread().getName(), name, initDate);
        System.out.printf("%s: Task %s: Started on %s\n",
                Thread.currentThread().getName(), name, new Date());
        try {
            long duration = (long)(Math.random() * 10);
            System.out.printf("%s: Task %s: Doing a task during %d seconds\n",
                    Thread.currentThread().getName(), name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: Task %s: Finished on: %s\n",
                Thread.currentThread().getName(), name, new Date());
    }
}
