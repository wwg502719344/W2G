package w2g_ConcurrentHashMap.w2g_concurrentHashMap_demo;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by W2G on 2018/7/11.
 */
public class ConcurrentLinkedQueueSource {
    ConcurrentLinkedQueue v=new ConcurrentLinkedQueue();

    //offer:新增方法
    /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never return {@code false}.
     *
     * 新增一个指定的元素到队列的尾部
     * 如果队列是无效的，这个方法不会返回值
     *
     * @return {@code true} (as specified by {@link Queue#offer})
     * @throws NullPointerException if the specified element is null
     */
    /*public boolean offer(E e) {
        //如果为null则抛出异常
        checkNotNull(e);

        //创建一个node节点
        final Node<E> newNode = new Node<E>(e);

        //从尾节点插入
        for (Node<E> t = tail, p = t;;) {   //p节点是尾节点
            //设q节点为新加入的node节点
            Node<E> q = p.next;

            //如果p节点为尾节点为真
            if (q == null) {
                // p is last node
                //将新节点通过cas的方式
                if (p.casNext(null, newNode)) {
                    // Successful CAS is the linearization point
                    // for e to become an element of this queue,
                    // and for newNode to become "live".
                    //当尾节点和最后一个节点之间间隔了不止一个节点的时候，设置最后一个节点为尾节点
                    if (p != t) // hop two nodes at a time
                        casTail(t, newNode);  // Failure is OK.
                    return true;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)
                // We have fallen off list.  If tail is unchanged, it
                // will also be off-list, in which case we need to
                // jump to head, from which all live nodes are always
                // reachable.  Else the new tail is a better bet.
                p = (t != (t = tail)) ? t : head;
            else
                // Check for tail updates after two hops.
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }*/
}
