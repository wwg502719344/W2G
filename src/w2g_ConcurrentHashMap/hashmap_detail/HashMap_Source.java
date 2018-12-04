package w2g_ConcurrentHashMap.hashmap_detail;

import java.util.HashMap;

/**
 * Created by W2G on 2018/11/26.
 * HashMap的结构采用了数组+链表+红黑树的构造，通过hash值，可以查找到数组对应的下标地址，具体节点我们需要通过链表一个一个的去找
 * 在put源码中，如果链表的长度超过了8个，将会被转为红黑树结构，用来提高查询的效率
 *
 * 在hashmap的结构中，采用了node的结构来对数据进行保存，Node由hash，value，key，next组成，构成了链表的基本结构
 * 在链表转为红黑树的过程中，node节点会被改造成treeNode，treeNode将会被放入到treeBin结构当中
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
     * 步骤1：查看数组大小，如果数组是空或者数组长度为0，则初始化数组，并且获取数组长度，此处无论数组是否为空，都可以获取到n值
     * 步骤2：如果hash值对应到数组位置到节点为null，则初始化节点并加入
     * 步骤3：数组在hash对应到位置有node节点
     * 步骤3-1：首先判断数组下标节点key值是否和我们插入数据的key值相同，并返回节点e
     * 步骤3-2：如果节点类型是红黑树类型则,则调用红黑树的插值方式
     * 步骤3-3：如果是链表类型首先判断查看该链表是否有相同key值的节点，如果没有则加入到链表尾部，如果链表长度超过8个，则转为数结构
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
        //如果新插入的值超过了数组的阈(yu)值，则对数组进行扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }*/

    /**
     * hashmap扩容操作
     * 首先进行新阈值和数组容量的扩充
     */
    /*
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        //获取原始数组的长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;//阈值
        int newCap, newThr = 0;
        //步骤一:获取扩容后的阈值和数组容量，创建新的数组
        //情景一:当数组长度大于0时，意味着这是一次扩容行为
        if (oldCap > 0) {
            //如果原始数组的长度超过最大，则将阈值设为最大，并不在继续扩容了，并返回数
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //如果原始数组大于初始化大小且扩大一倍小于最大容量，则原始数组扩大一倍
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
        //步骤二:数据迁移
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
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
