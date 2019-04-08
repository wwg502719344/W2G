package w2g_lock;

/**
 * Created by W2G on 2018/5/3
 * 重入锁相关源码
 */
public class w2g_reentrantLock {

    //ReentrantLockent r=new ReentrantLockent();
    //非公平性获取同步状态实现
    /**
     * 执行非公平的tryLock，tryAcquire由子类来实现
     * 但两者都需要非公平的尝试
     */
    /*final boolean nonfairTryAcquire(int acquires) {
        //获取当前线程
        final Thread current = Thread.currentThread();
        //state大于0表示锁被占用、等于0表示空闲，小于0则是重入次数太多导致溢出了
        int c = getState();
        //如果锁是空闲的
        if (c == 0) {
            通过CAS的方式
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }*/


}
