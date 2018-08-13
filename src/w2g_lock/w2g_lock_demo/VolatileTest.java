package w2g_lock.w2g_lock_demo;

import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/8/8.
 */
public class VolatileTest {

    static volatile int asa=1;

    public static void main(String args[])throws Exception{
        Thread a=new Thread(new TestVolatile(),"A");
        a.start();
        Thread b=new Thread(new TestVolatile2(),"B");
        b.start();
    }

    private static class TestVolatile implements Runnable {
        @Override
        public void run() {
            System.out.println("执行A线程");
            if (asa==1){
                System.out.println("初始A线程asa值为"+asa);
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("执行a为1的方法");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                asa+=1;
                System.out.println("最后A线程asa值为:"+asa);
            }
        }
    }

    private static class TestVolatile2  implements Runnable {
        @Override
        public void run() {
            asa=2;
            System.out.println("执行B线程");
            System.out.println("B线程将asa值改为"+asa);
        }
    }
}
