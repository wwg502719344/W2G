package w2g_thread_pool.pool_callable_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by W2G on 2018/9/27 0027.
 * 该类主要逻辑如下
 * 1:创建线程池，将实现了callable接口的实现类放进线程池的执行类中，注意是submit方法，会返回一个future对象
 * 2:获取future对象，可以获取异步计算的数据，如果还没有获取到数据，线程则会进行等待
 */
public class CallMain {
    public static void main(String[] args) {
        //创建固定长度为2的线程池
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.
                newFixedThreadPool(2);
        // 声明保存返回结果的列表，注意类型为Future<Integer>
        List<Future<Integer>> resultList = new ArrayList();
        Random random = new Random();

        // For循环中的submit方法在提交线程执行后会有一个返回类型为Future<Integer>的结果。将结果保存在列表中。
        for(int i = 0; i < 10; i++) {
            Integer number = random.nextInt(10);
            FactorialCalculator calculator =
                    new FactorialCalculator(number);
            Future<Integer> result = executor.submit(calculator);
            resultList.add(result);
        }

        System.out.printf("Main: Results\n");
        for(int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            Integer number = null;
            try {
                // 结果需要在线程执行完后才能get到，所以get执行时会使得线程等待，需要捕捉异常
                number = result.get();
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch(ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("Main: Task %d: %d\n", i, number);
        }

        // 关闭线程池
        executor.shutdown();
    }
}
