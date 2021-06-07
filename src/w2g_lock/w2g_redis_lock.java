package w2g_lock;


import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2019/5/24.
 * Redisson实现分布式锁功能
 * Redisson源码实现:
 * https://blog.csdn.net/qq_14828239/article/details/80578830
 */
public class w2g_redis_lock {

    public static void main(String[] args) throws Exception {/*
        Redisson redisson = Redisson.create();

        //此处设置锁的key值
        RLock lock = redisson.getLock("test");

        // 500ms是获取锁的超时时间，10000ms即10s是获取锁后的失效时间，实际失效时间是减去获取锁的时间
        boolean isLock = lock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
        try {
            System.out.println("test");
        }
        finally {
            lock.unlock();
        }
        redisson.shutdown();*/
    }

}
