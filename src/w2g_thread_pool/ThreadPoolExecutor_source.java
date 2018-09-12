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




}
