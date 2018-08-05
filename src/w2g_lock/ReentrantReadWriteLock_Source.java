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

    /**p1-读写锁中获取读锁相关代码
     *
     * Acquires the read lock.
     * 获取读锁
     * <p>Acquires the read lock if the write lock is not held by
     * another thread and returns immediately.
     * 如果写锁没有被其他线程获取或立即回来则获取读锁
     * <p>If the write lock is held by another thread then
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the read lock has been acquired.
     * 如果写锁被其他线程获取到了那么禁止一切线程调度并进行休眠直到回去到读锁
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
     * 成功获取则返回，否则线程进行排队，可能反复的进行阻塞和接触阻塞，
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


}
