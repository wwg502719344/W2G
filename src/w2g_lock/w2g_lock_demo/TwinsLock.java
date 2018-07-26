package w2g_lock.w2g_lock_demo;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by W2G on 2018/7/24 0024.
 */
public class TwinsLock implements Lock {

    private Sync sync=new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count<=0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        public int tryAcquireShared(int reduceCount){
            for (;;){
                int current=getState();
                int newCount=current-reduceCount;
                //当前资源数被正确更新或是小于0(小于0时可以未被正确cas)时返回
                if (newCount<0||compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }
    }


    @Override
    public void lock() {
        sync.acquireShared(1);//尝试获取锁资源
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
