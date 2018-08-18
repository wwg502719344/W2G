package w2g_queue;

import java.util.concurrent.DelayQueue;

/**
 * Created by W2G on 2018/8/17 0017.
 * 延迟队列源码解析
 * DelayQueue<E extends Delayed> :队列内部元素需要Delayer接口
 */
public class DelayQueue_Source {

    DelayQueue delayQueue=new DelayQueue();

    /*
    //reentrantLock实现线程的同步
    private final transient ReentrantLock lock = new ReentrantLock();

    //priorityQueue实现数据的保存
    private final PriorityQueue<E> q = new PriorityQueue<E>();


    //P1
    //插入元素到队列，元素要实现Delayed接口
    public boolean offer(E e) {
    //获取重入锁
    final ReentrantLock lock = this.lock;
    //获取锁资源
    lock.lock();
    try {
        //P2源码
        //元素加入优先级队列
        q.offer(e);
        //获取但不移除此队列的头
        //如果头元素就是元素e
        if (q.peek() == e) {（2）
            //设置当前等待线程的首线程为null
            leader = null;
            //唤醒等待队列
            available.signal();
        }
        return true;
    } finally {
        lock.unlock();
    }
}
    */

    /**
     * P2
     * 优先级队列下的offer方法
     */
    /*public boolean offer(E e) {
        if (e == null)  //如果传入的参数为空，则抛出异常
            throw new NullPointerException();
        modCount++;
        int i = size;
        if (i >= queue.length)
            grow(i + 1);
        size = i + 1;
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }*/

}
