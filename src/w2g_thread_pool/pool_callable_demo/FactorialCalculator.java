package w2g_thread_pool.pool_callable_demo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/9/27 .
 * 参考Task类
 */
public class FactorialCalculator implements Callable {

    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Object call() throws Exception {
        int result = 1;

        if(number == 0 || number == 1) {
            result = 1;
        } else {
            for(int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }
        System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
        return result;
    }
}
