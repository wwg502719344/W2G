package w2g_lock;

/**
 * Created by W2G on 2018/7/31 0031.
 * 读写锁相关源码解析
 */
public class w2g_ReentrantReadWriteLock {

    //获取写锁
    /*protected final boolean tryAcquire(int acquires) {
            *//*
             * Walkthrough:
             * 1. If read count nonzero or write count nonzero
             *    and owner is a different thread, fail.
             *    如果读的数量非0或者写的数量不是0或者当前线程不是获取锁的线程,fail
             * 2. If count would saturate, fail. (This can only
             *    happen if count is already nonzero.)
             *
             * 3. Otherwise, this thread is eligible for lock if
             *    it is either a reentrant acquire or
             *    queue policy allows it. If so, update state
             *    and set owner.
             *     这个线程是适合这个锁的如果获取成功的话,更新状态而且设为owner
             *//*
        Thread current = Thread.currentThread();
        int c = getState(); //获取当前状态
        int w = exclusiveCount(c);  //取同步状态的低16位，获取写状态
        if (c != 0) {   //如果当前有线程占用锁
            // (Note: if c != 0 and w == 0 then shared count != 0)
            //如果存在读锁，或者当前线程不是获取写锁的线程
            if (w == 0 || current != getExclusiveOwnerThread())
                return false;
            if (w + exclusiveCount(acquires) > MAX_COUNT)
                throw new Error("Maximum lock count exceeded");
            // Reentrant acquire
            setState(c + acquires);
            return true;
        }
        if (writerShouldBlock() ||
                !compareAndSetState(c, c + acquires))
            return false;
        setExclusiveOwnerThread(current);
        return true;
    }*/
}
