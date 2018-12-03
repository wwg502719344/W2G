package w2g_ConcurrentHashMap.hashmap_detail;

import java.util.HashMap;

/**
 * Created by W2G on 2018/11/26.
 */
public class HashMap_Source {

    HashMap map=new HashMap();

    /**
     * 该方法为hashmap中put操作的源码，整体思路分为两个方面
     * 1-根据新传入的参数判断数组中是否已经存在该key，如果存在，则对value进行覆盖，如果没有则进行添加
     * 2-对于新节点的添加，需要根据情况判断是加入到链表结构中还是树结构中
     *
     * 步骤1-2：hash值对应节点为null
     *
     * 步骤1：查看数组大小，如果数组是空或则数组长度为0，则初始化数组，并且获取数组长度，此处无论数组是否为空，都可以获取到n值
     * 步骤2：如果hash值对应到数组位置到节点为null，则初始化节点并加入
     * 步骤3：数组在hash对应到位置有node节点
     * 步骤3-1：
     */
    /*
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //步骤1:查看当前数组是否为空(长度是否为0)，如果判断条件为true，则对tab进行初始化，并对n进行赋值
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //步骤2:如果hash值在tab数组对应位置的节点为null，则初始化一个node放在对应的位置，此处的n是数组的长度1¡
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        //步骤3:数组该位置有数据
        else {
            Node<K,V> e; K k;
            //步骤3-1:首先判断该位置的第一个数据是不是当前节点，通过比较key来进行判断，如果是则赋值给节点e
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //步骤3-2:如果该节点是红黑树结构的节点，则通过红黑树的方式将该节点插入
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //步骤3-3:此处说明数据是链表结构
                for (int binCount = 0; ; ++binCount) {
                    //如果该链表节点没有传入的节点则创建一个节点添加到链表的尾部
                    //如果链表长度大于指定长度，则转为红黑树结构
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //链表中已经有相等的key，则进行值覆盖
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //此处的e代表的是已经存在相同key的老节点,由步骤3-1/2可知
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;//对e值进行覆盖
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }*/
}
