package w2g_concurrent_utils.demo;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by W2G on 2018/8/26 0026.
 *
 * 只有当CyclicBarrier构造函数中传入的参数都执行了await方法，才会继续执行后面的代码...
 * 否则不会继续往下执行
 * CyclicBarrier可以理解为程序屏障，程序运行到屏障则停止运行
 */
public class CyclicBarrier_demo {

    static CyclicBarrier cyclicBarrier=new CyclicBarrier(2);

    public static void main (String args[]){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     System.out.println("到达子线程输出");
                     cyclicBarrier.await();
                 } catch (Exception e) {
                 }
                 System.out.println("子线程输出");
             }
         }).start();

        try {
            //当所有线程都执行完await，才能执行继续执行线程，并发执行
            System.out.println("到达主线程输出");
            //cyclicBarrier.await();
        } catch (Exception e) {
        }
        System.out.println("主线程输出");
    }
}
