package w2g_thread_pool;


import java.util.concurrent.FutureTask;

/**
 * Created by W2G on 2018/9/10.
 * 该类源码解析主要是分析Future设计模式的实现原理
 * 实现类是FutureTask，实现类是RunnableFuture，RunnableFuture继承了Future和Runnable类
 * 执行实现类获取ExecutorService，去执行调用futureTask的run方法
 *
 * 基本思路:
 * 创建FutureTask实体类，传入需要计算结果的类作为参数，该类需要实现Callable类，重写call方法
 * 使用Execute类去执行FutureTask类，futureTask调用run方法，run方法调用call方法进行计算，
 * 将结果返回并调用FutureTask的set方法，将数据放入futureTask中
 *
 * 源码解析模块
 * P0:使用future模式，调用主方法逻辑
 * P1：源码类基本构成
 * P2：解析future实现异步获取数据
 * P2-1:获取实际数据,如果计算没有结束，则等待计算结果
 * p2-1-1:等待完成或是超时或者打断导致的终止
 * P2-2:将对应的值赋值给outcome
 * P2-2-1:唤醒并通知所有等待的线程继续执行各自的方法
 */
public class FutureTask_Source {

    FutureTask<String> futureTask = null;

    /**
     * P0:使用future模式，调用主方法逻辑
     * 情景:一个方法中由耗时较长的操作，我们可以把这个耗时较长的操作剥离出去再起一个线程进行执行
     * 思路流程:
     * 1创建额外的业务类，该类主要是封装了需要另外实现的业务
     * 2创建futureTask对象，将业务类写进构造方法当中
     * 3创建线程并启动
     * 4获取额外业务类中的返回数据
     */
    /*public static void main(String args[]){

        //创建FutureTask类，传入RealData类作为参数，RealData中封装了实际计算的方法
        FutureTask<String> futureTask = new FutureTask<>(new RealData("Hello"));

        //调用Executors类执行futureTask对象，启动执行FutureTask中的run方法
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(futureTask);

        System.out.println("请求完毕！");

        try {
            Thread.sleep(2000);
            System.out.println("这里经过了一个2秒的操作！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取实际数据，注意，如果实际数据没有计算出来，那么会进行await等待
        System.out.println("真实数据：" + futureTask.get());
    }*/


    /**
     * P1：源码类基本构成
     * FutureTask实现了RunnableFuture接口，RunnableFuture接口实现了Runnable接口和Fature接口
     * 目的是实现run方法，FutureTask类则是Future类的实现类
     */
    /*public class FutureTask<V> implements RunnableFuture<V> {
        private volatile int state;
        private static final int NEW          = 0;//初始化时是NEW
        private static final int COMPLETING   = 1;//任务完成但是尚未赋值给outcome
        private static final int NORMAL       = 2;//任务完成且赋值给outcome
        private static final int EXCEPTIONAL  = 3;//异常状态
        private static final int CANCELLED    = 4;//任务取消
        private static final int INTERRUPTING = 5;//任务打断
        private static final int INTERRUPTED  = 6;//


        // The underlying callable; nulled out after running
        // 是构造函数中传入的计算数据类，该类实现了Callable接口
        private Callable<V> callable;

        // The result to return or exception to throw from get()
        // 实际数据值
        private Object outcome; // non-volatile, protected by state reads/writes

        // The thread running the callable; CASed during run()
        // 运行callable的线程(实际计算的线程)
        private volatile Thread runner;


        //FutureTask的构造方法，传入实现了callable的数据类，对callable进行赋值，在run方法中进行调用
        public FutureTask(Callable<V> callable) {
            if (callable == null)
                throw new NullPointerException();
            this.callable = callable;
            this.state = NEW;       // ensure visibility of callable
    }
    }*/


    /**
     * P2：解析future实现异步执行获取callable数据
     * run方法启动异步线程，调用数据类，并返回计算结果，将数据封装进futureTask方法中
     */
    /*public void run() {
        //检查当前future线程的任务执行状态是否是可执行状态，如果不是则直接返回
        if (state != NEW ||
                !UNSAFE.compareAndSwapObject(this, runnerOffset,
                        null, Thread.currentThread()))
            return;
        try {
            Callable<V> c = callable;   //获取callable数据类
            if (c != null && state == NEW) {    //如果数据类不是null且状态为NEW
                V result;   //实际返回数据类
                boolean ran;
                try {
                    result = c.call();  //调用实际业务类重写的call方法，获取实际数据
                    ran = true;         //将ran设置为true
                } catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }
                if (ran)
                    set(result);    //P2-2:获取到实际参数后调用set方法将数据存进futureTask中
            }
        } finally {
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            int s = state;
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
    }
    */

    /**
     * P2-1:获取实际数据,如果计算没有结束，则等待计算结果
     */
    /*public V get() throws InterruptedException, ExecutionException {
        int s = state;
        if (s <= COMPLETING)
            s = awaitDone(false, 0L);   //p2-1:等待计算结果
        return report(s);//返回实际数据
    }*/

    /**
     * p2-1-1:等待完成或是超时或者打断导致的终止
     * Awaits completion or aborts on interrupt or timeout.
     *
     * @param timed true if use timed waits
     * @param nanos time to wait, if timed
     * @return state upon completion
     */
    /*
    private int awaitDone(boolean timed, long nanos)
            throws InterruptedException {
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        WaitNode q = null;//阻塞队列元素，被挂起的线程都会被封装成为该对象
        boolean queued = false;
        //1:如果一旦该线程被中断了，将会移除阻塞队列中的任务，因为futureTask设计有取消任务的操作

        //情景:任务还没有被完成
        //第一次循环，这时候s<COMPLETING,此时q==null,这时应该创建一个WaitNode对象，节点对象中的线程就是当前线程，继续进行循环
        //第二次循环，进入方法5，将创建的q加入到队列头当中
        //第三次循环，是否设置了超时时间，然后将线程挂起
        for (;;) {
            if (Thread.interrupted()) {
                removeWaiter(q);
                throw new InterruptedException();
            }

            int s = state;
            if (s > COMPLETING) { //2:如果任务完成或是已经取消了
                if (q != null) //如果队列元素有值，则将队列元素设置为null，并返回当前线程任务的状态
                    q.thread = null;
                return s;
            }

            else if (s == COMPLETING) // 3:如果任务完成了(正常/异常)还没有赋值给指定接受元素(不能超时)
                Thread.yield(); //重新选择可执行的线程，emmmmmm
            else if (q == null) //4:如果等待节点为空，则创建一个等待队列元素
                q = new WaitNode();
            //5:如果这个节点还没有加入到等待队列中，则将这个节点加入到队列的头部
            else if (!queued)
                queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                        q.next = waiters, q);//waiters是等待队列的头结点

            //6:如果设置了超时时间等待，则在指定的时间内移除等待对垒
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0L) {
                    removeWaiter(q);
                    return state;
                }
                LockSupport.parkNanos(this, nanos);
            }
            //7:否则阻塞线程，等待唤醒
            else
                LockSupport.park(this);
        }
    }*/


    /**
     * P2-2:将对应的值赋值给outcome
     */
    /*protected void set(V v) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = v;//将最终结果赋值给outcome
            UNSAFE.putOrderedInt(this, stateOffset, NORMAL); // final state
            finishCompletion();//P2-2-1:
        }
    }*/

    /**
     * P2-2-1:唤醒并通知所有等待的线程继续执行各自的方法
     * Removes and signals all waiting threads, invokes done(), and
     * nulls out callable.
     * 移除并通知所有等待的线程，执行done方法
     */
    /*
    private void finishCompletion() {
        // assert state > COMPLETING;
        //waiters不为空的时候,waiters是封装该futureTask对象的线程
        for (WaitNode q; (q = waiters) != null;) {
            //如果当前等待线程就是当前线程则置为null
            if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
                //当前循环体唤醒了当前futureTask中所有等待队列中的节点(挂起的线程)，目的是什么呢，被唤醒的线程做什么呢
                //被唤醒的线程继续执行各自线程栈中的awaitDone方法，尝试获取对应的结果
                for (;;) {
                    Thread t = q.thread;//获取当前节点中的线程
                    if (t != null) {//如果线程不为空
                        q.thread = null;//将等待节点的线程设置为null
                        LockSupport.unpark(t);//唤醒等待节点中的线程
                    }
                    WaitNode next = q.next;//获取等待节点的下一个节点
                    if (next == null)//如果等待队列已经没有等待节点了，则直接跳出当前循环
                        break;
                    q.next = null; // unlink to help gc
                    q = next;   //将下一个等待节点赋值给q节点
                }
                break;
            }
        }

        done();

        callable = null;        // to reduce footprint
    }
    */


}
