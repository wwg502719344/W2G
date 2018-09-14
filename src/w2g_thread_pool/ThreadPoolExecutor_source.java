package w2g_thread_pool;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 非常特殊的一个变量，他是用来表示线程池状态和当前线程数量的一个变量
     * 此处申明了final类型，说明AtomicInteger的方法不可以被继承，get和set方法不可被重写(本身已经被final所修饰)
     * runState：线程池运行状态
     * workerCount：工作线程的数量
     */
    //private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));


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
         * 的调用以原子方式检查运行状态和任务数量，以便防止出现false警告当不应该
         * 添加线程的时候
         *
         * 2方法也会启动一个新线程
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. 如果我们不能加入任务队列，我们就尝试在添加一个新的线程，如果添加失败了
         * 我们就应该知道我们已经停止了然后把reject任务
         *
        int c = ctl.get();
        //判断运行的线程是否小于核心线程数
        if (workerCountOf(c) < corePoolSize) {
            //启动新线程执行任务然后返回
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        //当线程池状态是否是运行状态且成功加入工作队列中
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

    /**
     * 核心方法,线程池启动线程的地方，实现run方法
     *
     */
    /*private boolean addWorker(Runnable firstTask, boolean core) {
        retry:// continue/break跳出标记位
        for (;;) {
            int c = ctl.get();//获取当前线程池状态和线程数量
            int rs = runStateOf(c);//获取当前线程状态

            // 检测队列是否为空，仅在必要的时候
            // 如果不满足
            if (rs >= SHUTDOWN &&
                    ! (rs == SHUTDOWN &&
                            firstTask == null &&
                            ! workQueue.isEmpty()))//如果线程池运行状态是shutdown且执行任务为空且工作队列为空
                return false;   //返回false

            for (;;) {
                int wc = workerCountOf(c);  //获取工作线程的数量
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))//如果工作线程数量大于最大数量或者工作线程数量是核心线程数...
                    return false;   //返回false
                if (compareAndIncrementWorkerCount(c))//比较并新增worker数量
                    break retry;//退出循环
                c = ctl.get();  // Re-read ctl
                if (runStateOf(c) != rs)
                    continue retry;
                // else CAS failed due to workerCount change; retry inner loop
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            w = new Worker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // Recheck while holding lock.
                    // Back out on ThreadFactory failure or if
                    // shut down before lock acquired.
                    int rs = runStateOf(ctl.get());

                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) // precheck that t is startable
                            throw new IllegalThreadStateException();
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            if (! workerStarted)
                addWorkerFailed(w);
        }
        return workerStarted;
    }*/
}
