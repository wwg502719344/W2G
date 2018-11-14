package w2g_thread_pool.demo;

/**
 * Created by W2G on 2018/11/14.
 */
public class TestInterrupt {

    public static void main(String args[]){
        //Thread.currentThread().interrupt();
        System.out.println("第一次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第二次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第3次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第4次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第5次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("第6次调用Thread.interrupted(),返回值："+Thread.interrupted());
        System.out.println("=================end===============================");

    }
}
