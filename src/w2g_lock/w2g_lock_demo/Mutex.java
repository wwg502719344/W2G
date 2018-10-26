package w2g_lock.w2g_lock_demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by W2G on 2018/7/21 0021.
 * 实现Lock接口，方便进行锁操作
 */
public class Mutex implements Lock {

    /**
     * 静态内部类，用于自定义同步器
     */
    private static class Sync extends AbstractQueuedSynchronizer{

        //表示该线程是否是被独占模式占用(同步器可重写方法)
        protected boolean isHeldExclusively(int acquires){
            return getState()==1;       //getState()获取当前同步状态的值
        }

        //独占式获取同步状态，需要先查询当前状态然后通过cas设置同步状态(可重写方法)
        public boolean tryAcquire(int acquires){
            //如果CAS成功，则该线程获取锁
            if (compareAndSetState(0,1)){
                //设置该线程为独占线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁
        protected boolean tryRelease(int releases){
            //如果当前同步状态是0，抛出异常(释放同步状态的前提就是同步状态不为0)
            if (getState()==0){
                throw new IllegalMonitorStateException();
            }

            //设置当前独占线程为空
            setExclusiveOwnerThread(null);
            setState(0);

            return true;
        }


        //返回一个Condition，每个Condition包含一个condition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    //将操作代理到sync上
    private final Sync sync=new Sync();

    /**
     *获取锁，当锁获得后,独占锁资源
     */
    @Override
    public void lock() {
        //实现详情查看源码AQS-P1
        sync.acquire(1);
    }

    /**
     *和lock差不多，但是该方法相应中断，如果该线程在同步队列中被中断了，则方法会抛出异常，并返回
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        //AQS提供的模版方法，不需要重写
        sync.acquireInterruptibly(1);
    }

    /**
     *独占是获取同步状态
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     *超时获取同步状态
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquire(1);
    }

    /**
     *解锁，释放资源
     */
    @Override
    public void unlock() {
        sync.release(1);
    }

    /**
     *返回一个CONDITION
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
