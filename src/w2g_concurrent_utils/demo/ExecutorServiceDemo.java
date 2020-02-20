package w2g_concurrent_utils.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by W2G on 2020/2/20.
 * 该demo测试主线程销毁线程池和子线程执行情况
 * 当主线程销毁线程池后，并不会销毁已经注册的子线程任务，子线程任务得以继续执行
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("东部测试");
                System.out.println("南部测试");
                System.out.println("西部测试");
                System.out.println("北部测试");
            }
        });
        System.out.println("主线程销毁线程池开始");
        executor.shutdown();
        System.out.println("主线程销毁线程池结束");
    }
}
