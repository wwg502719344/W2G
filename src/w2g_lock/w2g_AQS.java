package w2g_lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;

/**
 * Created by W2G on 2018/3/29.
 *  bookmark-4查看acquire方法
 */
public class w2g_AQS {

    /**
     * P1
     * Acquires in exclusive mode, ignoring interrupts.  Implemented
     * by invoking at least once {@link #tryAcquire},returning on success.
     * 独占式获取同步状态，忽略中断，实现并执行至少一次tryAcquire方法,返回success
     * Otherwise the thread is queued, possibly repeatedly blocking and unblocking,invoking {@link #tryAcquire} until success.
     * 如果返回false，线程加入队列进行阻塞
     * This method can be used to implement method {@link Lock#lock}.
     *  这个方法被用来实现Lock接口的lock方法的实现
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     */
    /*public final void acquire(int arg) {
        //自定义方法
        if (!tryAcquire(arg)
        //构造节点并加入到等待队列中去
        && acquireQueued(addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), arg)){
                    //如果acquireQueued返回true,则执行该行代码
                    //acquire(int arg)对中断不敏感，被中断后不会移除队列
                    //直到获取到资源为止，且整个过程忽略中断的影响
                    //返回true是因为之前被中断过，只不过在队列中中断的过程中未相应，所以当获取到同步状态的时候激活中断操作
                    selfInterrupt();
                }

    }

    //进入等待状态，当拿到资源后返回
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {  //将节点加入到等待队列中去直到成功
                final Node p = node.predecessor();  //返回上一个node节点
                if (p == head && tryAcquire(arg)) { //如果前一个节点是首节点或是当前获取同步状态的节点
                    setHead(node);  //设置当前节点为首节点
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                //如果自己可以休息了，就进入waiting状态，直到被unpark()
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true; ////如果等待过程中被中断过，哪怕只有那么一次，就将interrupted标记为true
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }


    //返回true表示当前线程被成功挂起
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        //r如果前驱节点是signal表示前驱结点会通知它，可以安心挂起
        if (ws == Node.SIGNAL)
             *//*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             *//*
             return true;
          //如果前节点是已经被取消的节点
          if (ws > 0) {
            *//*
             * Predecessor(前任，前辈) was cancelled. Skip over predecessors and
             * indicate(表明,指示) retry.
             *//*
                do {
                    node.prev = pred = pred.prev;
                } while (pred.waitStatus > 0);
                pred.next = node;
            } else {
                *//*
                 * waitStatus must be 0 or PROPAGATE.  Indicate that we
                 * need a signal, but don't park yet.  Caller will need to
                 * retry to make sure it cannot acquire before parking.
                 *//*
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL); //设置该节点的状态为SIGNAL
        }
        return false;
    }

    */







    /**
     * P2:共享式同步状态的获取
     * @param arg
     */
    /*
    public final void acquireShared(int arg) {
        //调用重写的tryAcquireShared方法，
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    private void doAcquireShared(int arg) {
        //将当前节点加入到等待队列中去并且设为共享模式
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                //获取当前节点的前驱节点
                final Node p = node.predecessor();
                if (p == head) {
                    //尝试获取同步状态
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        //设置当前节点为首节点并传播 and wake-up next node
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    */











    /**
     * P10
     * 共享式获取同步状态
     */
    /*
    //尝试获取同步状态
    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    //获取共享状态失败，进入等待队列，获取资源才返回
    private void doAcquireShared(int arg) {
        //构造共享节点加入队列
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    */





}
