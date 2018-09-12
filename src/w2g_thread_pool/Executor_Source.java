package w2g_thread_pool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Created by W2G on 2018/9/12.
 * 超级接口Executor
 * 主要是执行提交的runnable任务，此接口主要是实现了一种将任务提交与任务执行分离开来的方法，普通的线程执行时在线程创建后，在线程中...
 * 去实现业务，在传统方法中Thread和Runnable是紧耦合的，而Executor就是为了将任务提交和执行解耦，executor负责将任务进行提交，...
 * 具体任务在何时执行则由实现类决定，例如生产者-消费者模式
 */
public class Executor_Source {

    //P1:线程的执行调用并非全部都是异步
    //类DirectExecutor实现了Executor的接口，可以在调用类中立即运行已提交的任务
    //传统的方式需要同时使用Thread创建线程，在线程的实现类中需要实现Runnable接口
    //Q:在实现了Executor的实现类中，是如何调execute方法的呢
    /*class DirectExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }*/


    /**
     * 通常情况下，创建启动线程并不是在线程的调用中启动的，而是在任务的执行者中，为每一个任务生成一个调用线程
     * 任务需要实现runnable接口
     */
    /*class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }*/


    /**
     *
     */
    /*class SerialExecutor implements Executor {
        final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
        final Executor executor;
        Runnable active;

        SerialExecutor(Executor executor) {
            this.executor = executor;
        }

        public synchronized void execute(final Runnable r) {
            tasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((active = tasks.poll()) != null) {
                executor.execute(active);   //此处的实现方法是什么？
            }
        }
    }*/
}
