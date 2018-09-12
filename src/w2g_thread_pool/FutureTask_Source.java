package w2g_thread_pool;

import w2g_design_model.futureModel.RealData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
 *
 */
public class FutureTask_Source {

    FutureTask<String> futureTask = null;
    /**
     * P0:使用future模式，调用主方法逻辑
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
        private static final int NEW          = 0;
        private static final int COMPLETING   = 1;
        private static final int NORMAL       = 2;
        private static final int EXCEPTIONAL  = 3;
        private static final int CANCELLED    = 4;
        private static final int INTERRUPTING = 5;
        private static final int INTERRUPTED  = 6;


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
     * P2：解析future实现异步获取数据
     * run方法启动异步线程，调用数据类，并返回计算结果，将数据封装进futureTask方法中
     */
    /*public void run() {
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
                    result = c.call();  //调用callable方法的call方法，获取实际数据
                    ran = true;
                } catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }
                if (ran)
                    set(result);    //获取到实际参数后调用set方法将数据存进futureTask中
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
    }*/

    //获取实际数据,如果计算没有结束，则等待计算结果
    /*public V get() throws InterruptedException, ExecutionException {
        int s = state;
        if (s <= COMPLETING)
            s = awaitDone(false, 0L);   //p2-1:等待计算结果
        return report(s);//返回实际数据
    }*/
}
