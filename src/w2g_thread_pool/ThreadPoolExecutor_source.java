package w2g_thread_pool;

import java.util.concurrent.*;

/**
 * Created by W2G on 2018/8/30.
 * 线程池执行类源码解析
 * P1:ThreadPoolExecutor类所继承类极其相关关系
 * P2:线程池状态源码基本变量组成及解析
 * P3:ThreadPoolExecutor执行方法
 * P4:Worker中run方法的实现类
 */
public class ThreadPoolExecutor_source {

    ThreadPoolExecutor a=null;

    Executors e=null;

    ExecutorService executorService=null;

    //future设计模式,jdk内置实现
    FutureTask futureTask=null;


    /**
     * P1 执行类基本组成结构
     * ThreadPoolExecutor类继承AbstractExecutorService，AbstractExecutorService是executorService接口的实现类，
     * executorService是executor的子接口，线程池类
     * executor是超级接口，详情查看Executor_Source类及QA(Q8)
     *
     * executorService中的方法主要是对线程池任务进行控制管理，该类可以对任务进行执行和关闭等操作
     * AbstractExecutorService是线程池类的实现类，里面主要executorService方法的实现
     * ThreadPoolExecutor类内部通过调用线程池实现方法，对开发人员提供使用线程池的API
     *
     * P1-1 AbstractExecutorService是executorService的实现类，实现了相关方法
     * 核心点:大多数方法通过提交task返回future对象
     */
    /*

    public class ThreadPoolExecutor extends AbstractExecutorService {

        //省略
        ...
    }

    public abstract class AbstractExecutorService implements ExecutorService {

        //省略
        ...
    }

    public interface ExecutorService extends Executor {

        //省略
        ...
    }

    public interface Executor {

        void execute(Runnable command);
    }
    */


    /**
     * P2:线程池状态源码基本变量组成及解析
     * 变量ctl是非常特殊的一个变量，他是用来表示线程池状态和当前线程数量的一个变量
     * 此处申明了final类型，说明AtomicInteger类不可以被继承,值得注意的是AtomicInteger并不能理解为一个变量
     * 是一个原子整数，利用高低位包装了如下两个概念
     * runState：线程池运行状态，占据高三位，主要有RUNNING,SHUTDOWN,STOP,TIDYING,TERMINATED
     * workerCount：线程池中当前活动的线程数量，占据低29位
     **/
    /*
    //通过ctl获取runState和workerCount状态
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;//
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;//最大线程数容量

    private static final int RUNNING    = -1 << COUNT_BITS;//线程池可以接受新任务
    private static final int SHUTDOWN   =  0 << COUNT_BITS;//线程池不在接受新任务(当工作线程大于等于最大线程的时候)
    private static final int STOP       =  1 << COUNT_BITS;//线程池不在接受新任务，不在执行队列中的任务
    private static final int TIDYING    =  2 << COUNT_BITS;//线程池中所有任务均终止
    private static final int TERMINATED =  3 << COUNT_BITS;//线程池彻底终止

    // ctl操作
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    */

    /**
     * P3:ThreadPoolExecutor执行入口方法
     * 该方法接收一个实现了Runnable接口的对象
     * 执行给出的任务在未来的某个时候，这个任务由一个新线程或是线程池中的线程进行执行
     * 如果这个任务不能被正确执行，不管什么原因，都提交给RejectedExecutionHandler去处理
     */
    /*public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();

         * 代码逻辑主要是以下4种情况：
         * 1.如果运行的线程少于核心线程，启动一个新线程处理提交的任务,对addWorker
         * 的调用以原子方式,对addWorker的调用需要进行runState和workerCount
         * 的检查，为了防止false的警告在添加线程的时候当不应该添加的时候
         * 的调用以原子方式检查运行状态和任务数量，以便防止出现false警告当不应该
         * 添加线程的时候
         *
         * 2方法也会启动一个新线程
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
        //当线程池状态是运行状态且成功加入工作队列中
        //当workerCountOf(c)的数量大于等于corePoolSize的时候
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();//重新获取线程池运行状态
            if (! isRunning(recheck) && remove(command))//如果运行状态不是可运行且移除当前任务
                reject(command);//关闭当前任务
            else if (workerCountOf(recheck) == 0)//当线程池中的线程数是0时
                //如果发现没有worker，则会补充一个null的worker什么意思？为什么这么做
                //如果为null的话最后会被移除核心线程池，那这个操作到底有什么意义
                //Q16
                addWorker(null, false);//P3-1
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


        //第一阶段，主要作用是检查，检查线程池运行状态和活动线程数量是否符合要求，否则返回false

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


        //第二阶段：创建Worker对象，并启动线程


        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            //Worker类继承了AQS，相当于一个阻塞队列，入队阻塞，出队唤醒，worker
            //AQS采用的是CLH同步队列，并不是一般的阻塞队列
            //根据你给的task创建worker对象,通过ThreadFactory获取thread的引用
            //Worker的也是Runnable的实现类
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

                    //rs < SHUTDOWN表示线程池处于可运行状态
                    //Q1:不太理解这个if,条件rs == SHUTDOWN && firstTask == null时，不应该继续创建线程了吧
                    //A1:当
                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) // t为新创建的线程，为什么存活要抛出异常？(因为此时t还没有启动start操作)
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
            if (! workerStarted)//如果worker创建失败，则加入addWorkerFailed
                addWorkerFailed(w);
        }
        return workerStarted;
    }*/


    /**
     * P4:Worker中run方法的实现类
     */
    /*final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();//获取当前线程，即为正在执行的，创建了worker的线程
        Runnable task = w.firstTask;//获取封装进worker中的task任务
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            //P4-1:getTask()源码解析
            //1:如果创建该worker时传递的task不为空(当前worker对应的任务还没有执行)，则去执行task
            //2:如果worker中的task已经执行完了，则去检查是否还有task任务没有执行，如果有则获取workQueue的task并执行
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // 如果线程池停止了，确保线程被打断
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                // 如果没有被打断，第二种情况要求在清除中断时去处理shutdownNow方法的竞争
                if ((runStateAtLeast(ctl.get(), STOP) ||
                        (Thread.interrupted() &&
                                runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted())
                    wt.interrupt();//中断该线程
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();//futureTask执行的地方
                    } catch (RuntimeException x) {
                        thrown = x; throw x;
                    } catch (Error x) {
                        thrown = x; throw x;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;//任务变量变为null
                    w.completedTasks++;//之前线程的数量加1
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }*/


    /**
     * Performs blocking or timed wait for a task, depending on
     * current configuration settings, or returns null if this worker
     * must exit because of any of:
     * 1. There are more than maximumPoolSize workers (due to
     *    a call to setMaximumPoolSize).
     * 2. The pool is stopped.
     * 3. The pool is shutdown and the queue is empty.
     * 4. This worker timed out waiting for a task, and timed-out
     *    workers are subject to termination (that is,
     *    {@code allowCoreThreadTimeOut || workerCount > corePoolSize})
     *    both before and after the timed wait, and if the queue is
     *    non-empty, this worker is not the last thread in the pool.
     *
     * 执行阻塞或是定时等待任务，根据当前的配置，或者返回null如果这个worker因为如下任意原因必须退出
     * 1.这里有超过最大线程的worker线程
     * 2.线程池已经停止工作了
     * 3.队列已经空了
     * 4.这个工作线程等待任务超时
     * @return task, or null if the worker must exit, in which case
     *         workerCount is decremented
     */
    /*private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?

        for (;;) {
            int c = ctl.get();
            int rs = runStateOf(c);//获取当前线程池的运行状态

            // Check if queue empty only if necessary.
            // 检查队列是否为空仅在必要的时候
            // 如果线程池当前状态已经停止且队列是空的
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();//重新设置当前线程池状态，内部通过cas方式实现状态的修改
                return null;//返回
            }

            int wc = workerCountOf(c);//获取工作线程的数量

            // Are workers subject to culling?
            // 当前线程数是否大于核心线程数，是则返回true
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

            //如果工作线程的数量大于最大设定线程数量||(超时&&工作队列是空的)，则
            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))//cas减少任务数量
                    return null;
                continue;//继续进行循环
            }

            try {
                //获取并从workQueue中移除该任务
                //如果false则从队列中获取任务，如果为true则。。。
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                if (r != null)//如果获取的任务不为null则返回该对象，设置timeout为true
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }*/
}
