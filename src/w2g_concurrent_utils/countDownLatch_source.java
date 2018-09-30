package w2g_concurrent_utils;

/**
 * Created by W2G on 2018/8/26 0026.
 * 允许一个或多个线程等待其他线程完成操作
 * mac
 *
 */
public class countDownLatch_source {

    /**
     * P1
     * Synchronization control For CountDownLatch.
     * 被用作CountDownLatch的同步控制
     * Uses AQS state to represent count.
     * 使用AQS的state来代表
     *
     * 内部调用AQS作为同步组件来实现多线程传入N计数的作用
     */
    /*
     private static final class Sync extends AbstractQueuedSynchronizer {
         private static final long serialVersionUID = 4982264981922014374L;

         Sync(int count) {
             setState(count);
         }
         int getCount() {
             return getState();
         }

         //
         protected int tryAcquireShared(int acquires) {
             return (getState() == 0) ? 1 : -1;
         }

         protected boolean tryReleaseShared(int releases) {
             // Decrement count; signal when transition to zero
             for (;;) {
                 int c = getState();
                 if (c == 0)
                     return false;
                 int nextc = c-1;
                 if (compareAndSetState(c, nextc))
                     return nextc == 0;
             }
         }
     }
    */

    /**
     * P2:countDownLatch是如何阻塞主线程执行的
     *
     * 调用同步器方法，如果state为0，则返回1，不会进行阻塞，如果返回-1...
     * 执行doAcquireSharedInterruptibly(arg)方法，当前线程(主线程)进行阻塞
     *
     */
    /*
      public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
      }
    */
}
