package w2g_concurrent_utils.demo;

import java.util.concurrent.CountDownLatch;

/**
 * Created by W2G on 2018/8/26 0026.
 * 该方法的主要作用就是拦截主线程在countDownLatch完成之前
 * countDownLatch理解为闸门，只有countDown数量达到了，闸门才会打开
 *
 */
public class CountDownLatch_demo {
    static CountDownLatch countDownLatch=new CountDownLatch(4);

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("东部测试");
                //countDownLatch.countDown();
                System.out.println("南部测试");
                //countDownLatch.countDown();
                System.out.println("西部测试");
                //countDownLatch.countDown();
                System.out.println("北部测试");
                //countDownLatch.countDown();

            }
        }).start();

        //掉用await方法，阻塞主线程,只有当参数countDown为0才能执行主线程，否则无法执行主线程
        //countDownLatch.await();
        System.out.println("主线程测试");
    }
}
