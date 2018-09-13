package w2g_thread_pool;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by W2G on 2018/8/30.
 * 线程池执行类源码解析
 *
 */
public class ThreadPoolExecutor_source {

    ThreadPoolExecutor a=null;

    //future设计模式,jdk内置实现
    FutureTask futureTask=null;


    /**
     * P1 执行类基本组成结构
     * 继承类AbstractExecutorService，该类实现了executorService方法，
     * executorService是executor的子接口
     * executor是超级接口，详情查看Executor_Source类及QA(Q8)
     *
     * P1-1 AbstractExecutorService是executorService的实现类，实现了相关方法
     * 大多数方法通过提交task返回future对象
     */
    /*public class ThreadPoolExecutor extends AbstractExecutorService {
    }*/


    /**
     * ThreadPoolExecutor执行方法
     * 执行给出的任务在未来的某个时候，这个任务由一个新线程或是线程池中的线程进行执行
     * 如果这个任务不能被正确执行，不管什么原因，都提交给RejectedExecutionHandler去处理
     */
    /*public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

         * 代码逻辑主要是以下3个步骤：
         * 1.如果运行的线程少于核心线程，启动一个新线程处理提交的任务,对addWorker
         * 的调用以原子方式
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.

        int c = ctl.get();
        //判断运行的线程是否小于核心线程数
        if (workerCountOf(c) < corePoolSize) {
            //启动新线程执行任务
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }*/

}
