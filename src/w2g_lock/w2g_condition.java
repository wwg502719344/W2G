package w2g_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * Created by W2G on 2018/5/8 0008.
 * Condition接口
 * 提供类似Object的监视器方法，与lock配合实现等待/通知模式
 * AQS内部类ConditionObject，维护了首节点和尾节点
 */
/*public class w2g_condition implements Lock{

    //获取condition
    Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();

    //使当前线程等待直到被通知或是中断
    //如果当前线程中断，抛出中断异常
    //通过getState返回保存的锁状态
    //以保存状态作为参数执行释放操作，如果失败抛出异常
    //阻塞直到被通知或是被中断
    //以保存状态作为参数通过执行专业的acquire版本重新获得
    //如果被阻塞？？
    public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

}*/
