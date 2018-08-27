package w2g_queue;

import java.util.concurrent.DelayQueue;

/**
 * Created by W2G on 2018/8/17 0017.
 * 延迟队列源码解析
 * DelayQueue<E extends Delayed> :队列内部元素需要Delayer接口
 *
 *
 * 参考资料
 * https://www.jianshu.com/p/f79e4e2bd071
 */
public class DelayQueue_Source {

    DelayQueue delayQueue=new DelayQueue();

    /*
    //reentrantLock实现线程的同步
    private final transient ReentrantLock lock = new ReentrantLock();

    //priorityQueue实现数据的保存
    private final PriorityQueue<E> q = new PriorityQueue<E>();


    /**
     * P1
     * 将实现了Delay的元素加入到延迟队列中去
     * 当前元素节点加入到优先级队列中去(priorityQueue)
     * 如果优先级队列的首节点就是刚才加入的元素，则leader线程置为空，唤醒等待队列的头节点加入到同步队列中去
     * leader置为空，原因是什么？目的是什么？因为此时队列中只有一个元素，所以不需要leader一直在检查...
     * 而是去唤醒等待队列中的元素，目的可能是考虑到效率问题
     *
     *
     */
    /*public boolean offer(E e) {
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
            if (q.peek() == e) {
                //设置当前等待线程的首线程为null
                //此处使用了设计模式leader-follower模式，为了减少不必要的线程等待
                //此处leader设置为null，可能原因是因为此时的e是快要过期的元素，直接调用等待队列来获取元素,leader
                //此处将leader设置为null原因很简单，因为元素只有一个，而且还是没有被消费的元素，这时候就有唤醒等待...
                //队列中的follower线程
                leader = null;
                //唤醒等待队列线程
                available.signal();
                }
                return true;
            } finally {
                lock.unlock();
            }
        }*/


    /**
     * P1-1
     * 优先级队列下的offer方法
     * 将元素加入到优先级队列中去，设置操作优先队列的数量和队列元素的数量
     * 如果队列为空，则将当前加入的节点设为头节点，如果不为空，则调整新元素和之前元素的数据结构
     */
    /*public boolean offer(E e) {
        if (e == null)  //如果传入的参数为空，则抛出异常
            throw new NullPointerException();
        modCount++; //调用优先级队列的次数加一
        int i = size;   //获取这个优先级队列的元素数量
        if (i >= queue.length)  //如果元素数量大于队列中的元素数量，则进行扩容操作
            grow(i + 1);
        size = i + 1;   //队列中的数量+1
        if (i == 0) //如果队列中还没有相关元素，则当前元素设为首歌节点元素，否则完成节点插入的调整
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }*/


    /**
     * P2
     * 获取延迟队列中的元素
     * Retrieves and removes the head of this queue, waiting if necessary
     * 检索并移除这个队列的头部，等待(如果有必要的话)直到这个队列的过期元素可用
     * until an element with an expired delay is available on this queue.
     *
     * 首先获取优先队列的首个元素，如果为空则调用线程沉睡。
     * 如果优先级队列不为空，查看当前首元素是否到达过期时间，到达过期时间了就获取并移除队列
     * 如果没有到达过期时间，将first变量置为null(防止内存泄漏)，如果leader线程不为空则进入等待队列
     * 如果leader为空，则当前线程为leader，并限时进入等待队列中进行等待
     * 如果leader为空，队列中还有元素存在，则唤醒所有等待的follower线程
     * 继续循环，直到获取延时队列中的元素
     */
    /*public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                E first = q.peek(); //获取优先队列中的首个节点
                if (first == null)  //如果优先队列中没有节点，则该线程进入等待线程
                    available.await();
                else {  //如果首节点不为空
                    long delay = first.getDelay(NANOSECONDS);   //获取当前元素还需要延时多长时间
                    if (delay <= 0) //如果延时时间小于或是等于0，则移出队列
                        return q.poll();
                    first = null; // don't retain ref while waiting防止内存泄漏
                    if (leader != null) //说明leader线程正在工作，当前线程就进入等待队列中
                        available.await();//当前线程转变为follower线程
                    else {  //如果首节点不为空，延时时间还没到，没有相应的处理线程
                        Thread thisThread = Thread.currentThread(); //获取当前线程
                        leader = thisThread;    //当前线程设置为首线程
                        try {
                            available.awaitNanos(delay);    //限时进入等待队列中处理延时时间最小的元素
                        } finally {
                            if (leader == thisThread)
                                leader = null;  //执行事件之后，将leader线程置为null让给其他线程
                        }
                    }
                }
            }
        } finally {
            if (leader == null && q.peek() != null) //如果leader线程为null，优先级队列中还有元素，则唤醒通知队列中的线程
                available.signal();
            lock.unlock();
        }
    }*/




    /**
     * P2-1：关于队列元素中compareTo方法的源码解析
     * Inserts the specified element into this delay queue.
     * 插入指定的元素进入延迟队列中
     */
    /*public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            q.offer(e); //向优先级队列中添加元素P4-1
            if (q.peek() == e) {
                leader = null;
                available.signal();
            }
            return true;
        } finally {
            lock.unlock();
        }
    }*/


    /*
    //P4:对新加入的元素进行插入操作前的准备工作
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        modCount++;
        int i = size;//获取当前优先级队列元素数量
        if (i >= queue.length)//如果数量大于队列当前大小
            grow(i + 1);//进行扩容操作
        size = i + 1;//数量加1
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);//P4-2:对新加入的元素进行排序
        return true;
    }

    //P4-1
    //根据comparator判断优先级队列采用不同的排序方式，具体回家在研究一下
    //
    private void siftUp(int k, E x) {
        if (comparator != null)
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x);//P4-3这个方法采用自己元素实现的排序规则，具体在研究
    }

    //P4-2
    //对队列中的元素进行排序
    private void siftUpComparable(int k, E x) {
        //x元素继承实现了Comparable接口
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) { //当元素位置小于根节点的时候
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (key.compareTo((E) e) >= 0)//如果不满足比较原则退出循环
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }

    */
}
