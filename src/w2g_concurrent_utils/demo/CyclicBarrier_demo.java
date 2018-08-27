package w2g_concurrent_utils.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2018/8/26 0026.
 *
 * 只有当CyclicBarrier构造函数中传入的参数都执行了，才会返回相应的结果...
 * 否则不会返回任何东西
 */
public class CyclicBarrier_demo {

    static CyclicBarrier cyclicBarrier=new CyclicBarrier(3);

    public static void main (String args[]){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     cyclicBarrier.await();
                 } catch (Exception e) {
                 }
                 System.out.println(1);
             }
         }).start();

        try {
            cyclicBarrier.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
}
