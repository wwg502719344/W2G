package w2g_lock.w2g_lock_demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by W2G on 2018/7/31 0031.
 * 读写锁-相关源码解析ReentrantReadWriteLockSource，p1~p3
 * HashMap不是线程安全的类，在并发操作中容易造成环链，但是通过读锁和写锁来保证线程安全
 * 在进行get操作中，获取读锁，在进行并发访问时不会造成阻塞
 * 在进行put操作时，需要先获取写锁，这样在进行并发操作时会阻塞其他线程对读锁和写锁的获取
 *
 */
public class ReentrantReadWriteLockTest {
    static Map<String,Object> map=new HashMap<String, Object>();
    static ReentrantReadWriteLock reentrantReadWriteLock=new ReentrantReadWriteLock();

    static Lock r=reentrantReadWriteLock.readLock();
    static Lock w=reentrantReadWriteLock.writeLock();

    public static final Object get(String key){
        r.lock();//p1
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }

    }

    public static final Object set(String key,Object value){
        w.lock();
        try{
            return map.put(key, value);
        }finally {
            w.unlock();
        }
    }
}
