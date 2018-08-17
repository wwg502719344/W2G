package w2g_queue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by W2G on 2018/8/17
 * BlockingQueue是阻塞队列的父接口，实现包括了java7提供的6个阻塞队列
 *
 * ArrayBlockQueue
 * LinkedBlockQueue
 * PriorityBlockQueue
 * DelayQueue
 * SynchronousQueue
 * LinkedBlockQueue
 *
 *
 */
public class BlockingQueue_source {

    BlockingQueue a=null;

    /**
     * 向队列中添加指定的元素(如果立即可行)，成功时返回success，如果空间不够的话则抛出异常
     */
    /*boolean add(E e);*/


    /**
     * 将指定元素插入队列中(如果立即可行且不会违反容量限制)，成功时返回success，失败后返回false
     */
    /*boolean offer(E e)*/


    /**
     * 将指定的元素插入队列当中，等待空间变的可用(如果有必要)
     */
    /*void put(E e) throws InterruptedException;*/


    /**
     * 将指定的元素插入队列当中，在规定的等待时间前等待空间变的可用(如果有必要)
     */
    /*boolean offer(E e, long timeout, TimeUnit unit)
            throws InterruptedException;*/


    /**
     *获取然后移除队列的头节点，等待直到元素变的可用(如果有必要)
     */
    /*E take() throws InterruptedException;*/


    /**
     * 获取然后移除队列的头节点，在指定的时间前等待直到元素变的可用(如果有必要)
     */
    /*E poll(long timeout, TimeUnit unit)
            throws InterruptedException;*/
}
