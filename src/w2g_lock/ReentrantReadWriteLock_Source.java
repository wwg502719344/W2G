package w2g_lock;

/**
 * Created by W2G on 2018/8/5 0005.
 * 读写锁相关源码实现分析
 *
 * 读锁和写锁状态的实现:通过按位切割读写变量来区分，高16位表示读状态，低16位表示写状态
 *
 *
 */
public class ReentrantReadWriteLock_Source {

    ////////////////////////////p1-读写锁中获取读锁相关代码//////////////////////////////////////
    /**
     * Acquires the read lock.
     * 获取读锁
     * <p>Acquires the read lock if the write lock is not held by
     * another thread and returns immediately.
     * 如果写锁没有被其他线程获取或立即回来则获取读锁
     * <p>If the write lock is held by another thread then
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the read lock has been acquired.
     * 如果写锁被其他线程获取到了那么中止线程调度的目的并进行休眠直到回去到读锁
     */
    /*
    public void lock() {
        sync.acquireShared(1); //共享状态下获取锁资源
    }
    */
    /**
     *
     * Acquires in shared mode, ignoring interrupts.  Implemented by
     * first invoking at least once {@link #tryAcquireShared},
     * returning on success.  Otherwise the thread is queued, possibly
     * repeatedly blocking and unblocking, invoking {@link
     * #tryAcquireShared} until success.
     *
     * 共享模式下获取同步状态，忽略中断，通过执行至少一次tryAcquireShared方法来实现
     * 成功获取则返回，否则线程进行排队，可能反复的进行阻塞和解除阻塞，
     * 通过执行tryAcquireShared,直到成功
     *
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquireShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     */
    /*public final void acquireShared(int arg) {
        //尝试获取共享锁资源，如果没有获取到则进入同步队列进行等待
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }*/

    /*
     * Walkthrough:
     * 1. If write lock held by another thread, fail.
     *    如果写锁已经被其他线程获取，失败
     * 2. Otherwise, this thread is eligible for
     *    lock wrt state, so ask if it should block
     *    because of queue policy. If not, try
     *    to grant by CASing state and updating count.
     *    Note that step does not check for reentrant
     *    acquires, which is postponed to full version
     *    to avoid having to check hold count in
     *    the more typical non-reentrant case.
     *    否则，这个线程可以锁定状态，
     * 3. If step 2 fails either because thread
     *    apparently not eligible or CAS fails or count
     *    saturated, chain to version with full retry loop.
     */
    /*protected final int tryAcquireShared(int unused) {
        Thread current = Thread.currentThread();
        int c = getState();
        if (exclusiveCount(c) != 0 &&   //？？
                getExclusiveOwnerThread() != current)   //如果资源已经被独占且不是当前线程，则返回-1
            return -1;
        int r = sharedCount(c); //获取读锁目前被占用的数量
        if (!readerShouldBlock() &&  //如果读取器不需要排队，则通过cas的方式获取读锁
                r < MAX_COUNT &&
                compareAndSetState(c, c + SHARED_UNIT)) {
            if (r == 0) {   //如果读锁还没有被占用
                firstReader = current;  //当前线程为首个读线程
                firstReaderHoldCount = 1;   //读数量设为1
            } else if (firstReader == current) {    //如果当前线程就是首个读线程
                firstReaderHoldCount++; //重入数量加一
            } else {
                HoldCounter rh = cachedHoldCounter; //如果
                if (rh == null || rh.tid != getThreadId(current))
                    cachedHoldCounter = rh = readHolds.get();
                else if (rh.count == 0)
                    readHolds.set(rh);
                rh.count++;
            }
            return 1;
        }
        return fullTryAcquireShared(current);
    }*/






    ////////////////////////////p2-读写锁中获取写锁相关代码//////////////////////////////////////
    /**
     * Acquires the write lock.
     * 获取写锁
     * <p>Acquires the write lock if neither the read nor write lock
     * are held by another thread
     * and returns immediately, setting the write lock hold count to
     * one.
     * 如果其他线程没有获取读锁和写锁，则获取锁资源并返回
     * <p>If the current thread already holds the write lock then the
     * hold count is incremented by one and the method returns
     * immediately.
     * 如果当前线程已经获取写锁，那么获取数量增加
     * <p>If the lock is held by another thread then the current
     * thread becomes disabled for thread scheduling purposes and
     * lies dormant until the write lock has been acquired, at which
     * time the write lock hold count is set to one.
     * 如果锁资源已经被其他线程获取，则当前线程在线程调度中终止并休眠直到成功获取写锁资源，并且写锁数量为1
     */
    /*public void lock() {
        sync.acquire(1); //尝试获取锁资源
    }*/

    /*protected final boolean tryAcquire(int acquires) {*/
        /*
         * Walkthrough:
         * 1. If read count nonzero or write count nonzero
         *    and owner is a different thread, fail.
         *    如果读锁数量或者写锁数量非0而且拥有锁资源的线程是非当前线程，失败
         * 2. If count would saturate, fail. (This can only
         *    happen if count is already nonzero.)
         *    如果获取锁的总数量已经饱和了，失败
         * 3. Otherwise, this thread is eligible for lock if
         *    it is either a reentrant acquire or
         *    queue policy allows it. If so, update state
         *    and set owner.
         *    否则，这个线程就符合获取锁的要求只要她是reentrant获取或是队列中允许获取的
         *    如果是这样，更新状态然后设置当前线程为当前拥有资源线程
         */
        /*
        Thread current = Thread.currentThread();//获取当前线程
        int c = getState();//获取同步状态
        //exclusiveCount方法取低16位，返回“写锁”重入次数
        int w = exclusiveCount(c);
        if (c != 0) {
            // (Note: if c != 0 and w == 0 then shared count != 0)
            //存在读锁或者当前线程不是已经获取写锁的线程
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
