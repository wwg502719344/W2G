package w2g_thread_pool.demo;

import w2g_thread_pool.pool_callable_demo.CallMain;
import w2g_thread_pool.pool_callable_demo.FactorialCalculator;
import w2g_thread_pool.pool_run_demo.RunMain;
import w2g_thread_pool.pool_run_demo.Server;
import w2g_thread_pool.pool_run_demo.Task;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Created by W2G on 2018/9/27 .
 * 该demo主要是实现两个基本的线程池的实现逻辑方法，区别如下
 * P1:业务类实现了Runnable接口，重写了run接口,任务提交到线程池通过execute方法进行执行，该方法不会返回计算结果
 * P2:业务类实现了callable接口，重写了call接口,任务提交到线程池通过submit方法进行执行，该方法会返回计算结果
 * 参考材料
 * http://shmilyaw-hotmail-com.iteye.com/blog/1765751
 */
public class ThreadPoolExecutor_FutureTask_Demo {

    //P1涉及类
    Task task=null;//该类为业务实现类，实现了线程池中的run方法
    Server server=null;//该类为创建线程池的线程类，也是线程池执行线程的入口类
    RunMain runMain= null;//代码入口类，创建server对象，传入task对象，执行

    //P2涉及类
    FactorialCalculator factorialCalculator=null;//实现了callable接口的业务类
    CallMain callMain=null;//该类创建线程池并提交执行业务类，可以获取返回的数据
}
