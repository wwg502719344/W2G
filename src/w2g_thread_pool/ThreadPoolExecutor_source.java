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
     * P2:线程池状态源码基本变量组成及解析
     *
     **/
    /*
    //非常特殊的一个变量，他是用来表示线程池状态和当前线程数量的一个变量
    //此处申明了final类型，说明AtomicInteger的方法不可以被继承，get和set方法不可被重写(本身已经被final所修饰)
    //runState：线程池运行状态
    //workerCount：工作线程的数量
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;//
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;//最大线程数容量

    private static final int RUNNING    = -1 << COUNT_BITS;//线程池可以接受新任务
    private static final int SHUTDOWN   =  0 << COUNT_BITS;//线程池不在接受新任务
    private static final int STOP       =  1 << COUNT_BITS;//线程池不在接受新任务，不在执行队列中的任务
    private static final int TIDYING    =  2 << COUNT_BITS;//线程池中所有任务均终止
    private static final int TERMINATED =  3 << COUNT_BITS;//

    // ctl操作
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    */

    /**
     * P3:ThreadPoolExecutor执行方法
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
            if (addWorker(command, true))//P3-1:核心代码
                return;
            c = ctl.get();
        }
        //当线程池状态是否是运行状态且成功加入工作队列中
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                //
                addWorker(null, false);//P3-1:核心代码
        }
        else if (!addWorker(command, false))
            reject(command);
    }*/

    /**
     * P3-1:核心方法,线程池启动线程的地方，实现run方法,主要是分为两个阶段
     * 第一阶段，主要作用是检查，检查线程池运行状态和活动线程数量相关问题
     *
     */
    /*private boolean addWorker(Runnable firstTask, boolean core) {


        //第一阶段，主要作用是检查，检查线程池运行状态和活动线程数量相关问题

        retry:// continue/break跳出标记位，其中break表示要跳过这个标记的循环，continue表示从这个标记的循环继续执行
        for (;;) {
            int c = ctl.get();//获取当前线程池状态和线程数量
            int rs = runStateOf(c);//获取线程池运行状态

            // 检查当前线程池状态，如果状态大于SHUTDOWN则直接返回false
            if (rs >= SHUTDOWN &&
                    ! (rs == SHUTDOWN &&
                            firstTask == null &&
                            ! workQueue.isEmpty()))//如果线程池运行状态是shutdown且执行任务为空且工作队列为空
                return false;   //返回false

            //该循环主要是检测线程池中活动线程数量是否在合理的范围之内，否则返回false
            //如果活动线程数在规定的范围之内，则更新活动线程数并跳出循环执行work进行创建线程
            for (;;) {
                int wc = workerCountOf(c);  //获取工作线程的数量
                //如果工作线程数量大于活动线程数或者大于核心线程数
                //core为true则是核心线程数
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;   //返回false
                //比较当前值是否和c相同，如果相同则c+1并直接跳出大循环，执行work进行线程的创建
                if (compareAndIncrementWorkerCount(c))
                    break retry;//退出循环
                c = ctl.get();  // Re-read ctl
                if (runStateOf(c) != rs)//如果当前的线程运行状态不是rs(方法开头获取的运行状态)
                    continue retry;
                // else CAS failed due to workerCount change; retry inner loop
            }
        }


        //第二阶段：创建Worker，并启动线程


        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            //根据你给的task创建worker对象,通过ThreadFactory获取thread的引用
            //Worker的也是Runnable的实现类
            //Worker类继承了AQS，相当于一个阻塞队列，入队阻塞，出队唤醒
            w = new Worker(firstTask);
            //因为在构造方法中不可以创建线程，所以此处将引用赋值并新创建的线程
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // 当加锁后重新检查线程池运行状态
                    // 当线程工厂失败就回退，或者在获取锁之前状态就shutdown
                    // Back out on ThreadFactory failure or if
                    // shut down before lock acquired.
                    int rs = runStateOf(ctl.get());

                    //不太理解这个if,条件rs == SHUTDOWN && firstTask == null时，不应该继续创建线程了吧
                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) // t为新创建的线程，为什么存活要抛出异常？
                            throw new IllegalThreadStateException();

                        workers.add(w); //将创建的线程添加到worker容器中，workers也可移除
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;//此处的作用是启动该线程
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {//为true时启动线程
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            if (! workerStarted)//如果worker创建失败
                addWorkerFailed(w);
        }
        return workerStarted;
    }*/
}
