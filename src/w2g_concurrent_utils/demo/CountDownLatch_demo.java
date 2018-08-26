package w2g_concurrent_utils.demo;

import java.util.concurrent.CountDownLatch;

/**
 * Created by W2G on 2018/8/26 0026.
 * 该方法的主要作用就是拦截主线程在countDownLatch完成之前
 *
 */
public class CountDownLatch_demo {
    static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                countDownLatch.countDown();
                System.out.println("2");
                countDownLatch.countDown();

                System.out.println("3");
                countDownLatch.countDown();
                System.out.println("4");
                countDownLatch.countDown();

            }
        }).start();


        countDownLatch.await();//此处不掉用await方法，就无法阻塞主线程
        System.out.println("主线程座下第一大将");

    }
}
