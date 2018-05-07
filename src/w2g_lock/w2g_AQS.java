package w2g_lock;

/**
 * Created by W2G on 2018/3/29.
 */
public class w2g_AQS {

    /**
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
