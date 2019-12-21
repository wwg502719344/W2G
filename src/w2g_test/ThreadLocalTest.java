package w2g_test;

/**
 * Created by W2G on 2019/12/19.
 * 将线程中的某个值和保存值的对象关联起来，确保共享变量和关联对象的关系
 */
public class ThreadLocalTest {

    ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
    ThreadLocal<String> stringLocal = new ThreadLocal<String>();


    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }


    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest test = new ThreadLocalTest();


        test.set();
        System.out.println("首次获取main线程id===="+test.getLong());
        System.out.println("首次获取main线程名称===="+test.getString());


        Thread thread1 = new Thread(() -> {
            test.set();
            System.out.println("获取thread1线程id===="+test.getLong());
            System.out.println("获取thread1线程id===="+test.getString());
        });
        thread1.start();
        thread1.join();

        System.out.println("最后获取main线程id===="+test.getLong());
        System.out.println("最后获取main线程id===="+test.getString());
    }

}
