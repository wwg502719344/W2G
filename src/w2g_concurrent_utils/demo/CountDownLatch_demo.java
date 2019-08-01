package w2g_concurrent_utils.demo;

import java.util.concurrent.CountDownLatch;

/**
 * Created by W2G on 2018/8/26 0026.
 * 该方法的主要作用就是拦截主线程在countDownLatch完成之前
 *
 */
public class CountDownLatch_demo {
    static CountDownLatch countDownLatch=new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("老夫东部第一斗皇");
                countDownLatch.countDown();
                System.out.println("在下南海第一斗皇");
                countDownLatch.countDown();

                System.out.println("洒家西域第一斗皇");
                countDownLatch.countDown();
                System.out.println("老子北狄第一斗皇");
                countDownLatch.countDown();

            }
        }).start();

        //掉用await方法，阻塞主线程,只有当参数countDown为0才能执行主线程，否则无法执行主线程
        countDownLatch.await();
        System.out.println("吾乃中土世界第一斗皇");

    }
}
