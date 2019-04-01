package w2g_ConcurrentHashMap.hashmap_detail;

import java.util.HashMap;

/**
 * Created by W2G on 2018/11/26.
 * HashMap的结构采用了数组+链表+红黑树的构造，通过hash值，可以查找到数组对应的下标值，在找到节点位于数组的下标位置后，
 * tab[i]会有三种类型，
 * 单个node节点:此时只有一个node占据数组的位置
 * 链表结构:这里对应的是node组成的链表，如果长度超过8个，会转为红黑树结构
 * 树结构:普通的链表节点为node节点，在树结构中将会转变为treeNode节点存放于treebin中
 *
 * 在hashmap的链表结构中，采用了node结构来对数据进行保存，Node由hash，value，key，next组成，构成了链表的基本结构
 * 在链表转为红黑树的过程中，node节点会被改造成treeNode，treeNode将会被放入到treeBin结构当中
 *
 * hashmap在多线程情况下出现的问题
 *
 * 1-数据不一致
 * 假如有两个线程A,B进行put操作，其中A线程计算了要插入的位置，但这时候B线程获取执行权，然后将数据插入到hashmap找那个
 * 这时A线程在进行插入，就会覆盖B线程插入的数据，造成数据的丢失
 *
 * 2-扩容过程中出现的死循环问题
 * 对于1.8以前的hashmap在进行扩容的过程可能会导致死循环的问题
 * 其原因在扩容过程中链表可能会形成环链
 * 在1.8中是通过将数据填入新数组中完成数据的赋值，不存在环链的问题
 *
 *
 */
public class HashMap_Source {

    HashMap map=new HashMap();

    /**
     * 该方法为hashmap中put操作的源码，整体思路分为两个方面
     * 1-根据新传入的参数判断数组中是否已经存在该key，如果存在，则对value进行覆盖，如果没有则进行添加
     * 2-对于新节点的添加，需要根据情况判断是加入到链表结构中还是树结构中
     *
     * 步骤1：查看数组大小，如果数组是空或者数组长度为0，则初始化数组，并且获取数组长度，此处无论数组是否为空，都可以获取到数组长度
     * 步骤2：查询hash值对应数组下标位置节点为null，则初始化节点并加入
     * 步骤3：查询hash值对应数组下标位置节点不为null，根据不同节点类型进行相应操作
     * 步骤3-1：首先判断数组下标节点key值是否和我们插入数据的key值相同，并返回节点e
     * 步骤3-2：如果节点类型是红黑树类型则,则调用红黑树的插值方式
     * 步骤3-3：如果是链表类型首先判断查看该链表是否有相同key值的节点，如果没有则加入到链表尾部，如果链表长度超过8个，则转为数结构
     */
    /*
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //步骤1:查看当前数组是否为空(长度是否为0)，如果判断条件为true，则对tab进行初始化
        //n为数组长度赋值
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //步骤2:如果hash值在tab数组对应位置的节点为null，则初始化一个node放在对应的位置
        //i = (n - 1) & hash可得出hash对应的数组下标
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
                    if ((e = p.next) == null) {//当前节点是链表节点的尾节点
                        p.next = newNode(hash, key, value, null);
                        //如果链表长度大于指定长度，则转为红黑树结构
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
        //如果新插入的值超过了数组的阈(yu)值，则对数组进行扩容
        //此处暴露线程不安全的问题
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }*/

    /**
     * hashmap扩容操作，主要做了两件事
     * 1，首先进行新阈值和数组容量的扩充
     * 2，其次是进行数据的迁移
     *
     * 对于数据迁移，就是对三种数据结构的转移
     * 数组下标单节点:该情况下直接赋值新的数据
     * 数组下标红黑树结构:该情况下调用红黑树的赋值方法
     * 数组下标链表结构
     *
     * (e.hash & oldCap)作用
     * 元素的在数组中的位置是否需要移动
     * 示例1:
     * e.hash=10 0000 1010
     * oldCap=16 0001 0000
     * &; =00000 0000比较高位的第一位 0
     * 结论:元素位置在扩容后数组中的位置没有发生改变
     * 示例2:
     * e.hash=17 0001 0001
     * oldCap=16 0001 0000
     * &; =10001 0000比较高位的第一位 1
     * 结论:元素位置在扩容后数组中的位置发生了改变,新的下标位置是原下标位置+原数组长度
     * https://m.aliyun.com/jiaocheng/778224.html
     *
     * 链表赋值
     * 对于扩容后的hashmap赋值操作，需要注意的是，对于链表结构分为两个部分进行操作
     * 在赋值前准备4个node对象节点，
     * loHead,loTail用于保存扩容前链表的首尾节点，此链表结构不需要变换链表位置和结构
     * hiHead，hiHead用于保存扩容后新增加的链表节点
     * 对于不需要移动位置的链表节点，按照之前链表的顺序排列，按照之前的顺序重新排列一遍，然后将首节点元素保存进数组的下标位置
     * 对于加入到扩容后位置的节点，与上同理，首节点存放如扩容后的下标节点位置
     *
     *
     *
     */
    /*
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;//原始数组的长度
        int oldThr = threshold;//阈值
        int newCap, newThr = 0;
        //步骤一:获取扩容/初始化后新数组的阈值和长度
        //情景一:数组扩容操作
        if (oldCap > 0) {
            //如果原始数组的长度超过最大，则将阈值设为最大，并不在继续扩容了，并返回数
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //如果原始数组大于初始化大小且扩大一倍小于最大容量，则原始数组阈值扩大1倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        //情景2-初始化行为，此时数组长度为0但是已设定阈值，在HashMap(int initialCapacit)进行put操作时触发
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        //对应使用 new HashMap() 初始化后，第一次 put 的时候，设置默认的 阈值和数组长度
        else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        //没整明白
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }

        threshold = newThr;
        //根据newCap创建一个新的数组，如果是初始化数组，到这里就结束了
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        //初始化操作到这里就结束了，下面是数组扩容操作的数据迁移过程
        //步骤二:数据迁移
        if (oldTab != null) {   //扩容操作
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    //情景1：如果该数组下标只有单个节点，则直接迁移该节点
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    //情景2：此处是红黑树类型的迁移
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // 此处是处理链表的结构

                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;

                        //链表赋值
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                //如果原元素位置没有发生改变
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }*/

}
