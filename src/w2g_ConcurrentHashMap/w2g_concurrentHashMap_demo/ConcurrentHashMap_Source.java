package w2g_ConcurrentHashMap.w2g_concurrentHashMap_demo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by W2G on 2018/11/26.
 */
public class ConcurrentHashMap_Source {

    ConcurrentHashMap map=new ConcurrentHashMap();
    HashMap hashMap=new HashMap();

    //////////////////BEGIN////内部类Node////BEGIN////////////////
    /**
     * Node类是ConcurrentHashMap存储结构的基本单元,继承于HashMap中的Entry,用于存储数据
     * Node是最核心的内部类，它包装了key-value键值对，所有插入ConcurrentHashMap的数据都包装在这里面。
     * 它与HashMap中的定义很相似，但是有一些差别它对value和next属性设置了volatile同步锁(与JDK7的Segment相同)，
     * 它不允许调用setValue方法直接改变Node的value域，它增加了find方法辅助map.get()方法。
     * 在hashmap中value和next并不是volatile类型的且setValue是可以进行修改的
     *
     */
    /*static class Node<K,V> implements Map.Entry<K,V> {  //Map.Entry表示一个映射项

        final int hash;
        final K key;
        volatile V val;     //volatile，保证可见性
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }
        */

        /**
         * hashcode方法返回的整数一般是用来确定对象被存储在Hashmap结构中的位置
         * @return
         */
        /*
        public final int hashCode()   {
            return key.hashCode() ^ val.hashCode();
        }


        // 不允许修改value值，HashMap允许
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        */
    /**
     * equals方法
     * @param o
     * @return
     *//*
        public final boolean equals(Object o) {
            Object k, v, u;
            Map.Entry<?,?> e;
            return (
                (o instanceof Map.Entry) &&     //传递的对象是Map.Entry类型
                (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&       //o强转Entry后key值不为空
                (v = e.getValue()) != null &&       //o强转Entry后value不为空
                (k == key || k.equals(key)) &&      //
                (v == (u = val) || v.equals(u))     //
            );
        }
*/
     /**
     * Virtualized support for map.get(); overridden in subclasses.
     * 辅助get方法，在子类中覆盖
     * @param h hash值(猜测)
     * @param k
     * @return
     *//*
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null); //如果还有后继节点则继续进行比较
            }
            return null;
        }
    }*/
    ///////////////////////////内部类Node-END///////////////////////////////
















    ///////////////////////////内部类TreeNode-BEGIN///////////////////////////////

    /**
     * Nodes for use in TreeBins
     * 被放入到TreeBins中被包装的Node
     * 应当是链表转成红黑树的时候，链表节点需要被转换成TreeNode(有待考证)
     * TreeNode继承自Node对象，
     * @param <K>
     * @param <V>
     */
    /*static final class TreeNode<K,V> extends Node<K,V> {
        TreeNode<K,V> parent;  // red-black tree links：父节点
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;

        TreeNode(int hash, K key, V val, Node<K,V> next,
                 TreeNode<K,V> parent) {
            super(hash, key, val, next);
            this.parent = parent;
        }


        Node<K,V> find(int h, Object k) {
            return findTreeNode(h, k, null);
        }

        *//**
     * Returns the TreeNode (or null if not found) for the given key
     * starting at given root.
     * 返回给定key值的TreeNode,没有找到就返回null,从给定的root节点开始
     */
      /*
        final TreeNode<K,V> findTreeNode(int h, Object k, Class<?> kc) {
            if (k != null) {
                TreeNode<K,V> p = this;
                do  {
                    int ph, dir; K pk; TreeNode<K,V> q;
                    TreeNode<K,V> pl = p.left, pr = p.right;
                    if ((ph = p.hash) > h)
                        p = pl;
                    else if (ph < h)
                        p = pr;
                    else if ((pk = p.key) == k || (pk != null && k.equals(pk)))
                        return p;
                    else if (pl == null)
                        p = pr;
                    else if (pr == null)
                        p = pl;
                    else if ((kc != null ||
                            (kc = comparableClassFor(k)) != null) &&
                            (dir = compareComparables(kc, k, pk)) != 0)
                        p = (dir < 0) ? pl : pr;
                    else if ((q = pr.findTreeNode(h, k, kc)) != null)
                        return q;
                    else
                        p = pl;
                } while (p != null);
            }
            return null;
        }
    }

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType)t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }*/
    ///////////////////////////内部类TreeNode-END///////////////////////////////








    ///////////////////////////内部类TreeBin-BEGIN///////////////////////////////
    /**
     * TreeNodes used at the heads of bins. TreeBins do not hold user
     * keys or values, but instead point to list of TreeNodes and
     * their root. They also maintain a parasitic read-write lock
     * forcing writers (who hold bin lock) to wait for readers (who do
     * not) to complete before tree restructuring operations.
     *
     * treebin用于封装treenode用的，
     */
//  static final class TreeBin<K,V> extends Node<K,V> {
        /*
        TreeNode<K, V> root;
        volatile TreeNode<K, V> first;
        volatile Thread waiter;
        volatile int lockState;
        // values for lockState
        static final int WRITER = 1; // set while holding write lock
        static final int WAITER = 2; // set when waiting for write lock
        static final int READER = 4; // increment value for setting read lock
        */

    /**
     * Creates bin with initial set of nodes headed by b.
     * 以b为初始化首节点创建bin
     *
     * 此处逻辑为
     * 1:根据table中index位置的链表，生成以b为头节点的TreeNode链表
     * 2;根据b为头节点，生成一个Treebin，并把树节点的root写入到table的index的内存中去
     */
    /*
      TreeBin(TreeNode<K,V> b) {
        super(TREEBIN, null, null, null);//初始化树根节点
        //b为首节点
        this.first = b;
        TreeNode<K,V> r = null;

        //此处的(，next)是申明变量next，在方法内部会对next进行赋值，结束方法体后将next赋值给x再进行循环体中的操作
        for (TreeNode<K,V> x = b, next; x != null; x = next) {
            next = (TreeNode<K,V>)x.next;//获取当前节点的后继节点
            x.left = x.right = null;
            //如果是首节点，将首节点的值赋给r
            if (r == null) {
                x.parent = null;
                x.red = false;
                r = x;
            }
            else {  //说明不是首节点

                //获取当前节点的key和hash值
                K k = x.key;
                int h = x.hash;
                Class<?> kc = null;

                //注意此处的r为当前节点的前驱节点
                for (TreeNode<K,V> p = r;;) {
                    int dir, ph;
                    //获取p节点的key
                    K pk = p.key;
                    if ((ph = p.hash) > h)   //如果前驱节点的hosh大于后继节点的hash，则赋值dir为-1，否则1
                        dir = -1;
                    else if (ph < h)
                        dir = 1;
                    //当两者hash值相同时
                    else if ((kc == null &&
                            (kc = comparableClassFor(k)) == null) ||
                            (dir = compareComparables(kc, k, pk)) == 0)
                        dir = tieBreakOrder(k, pk);
                    TreeNode<K,V> xp = p;
                    if ((p = (dir <= 0) ? p.left : p.right) == null) {
                        x.parent = xp;
                        if (dir <= 0)
                            xp.left = x;
                        else
                            xp.right = x;
                        r = balanceInsertion(r, x);
                        break;
                    }
                }
            }
        }
        this.root = r;
        assert checkInvariants(root);
      }*/
//   }



    ///////////////////////////内部类TreeBin-END///////////////////////////////







    ///////////////////////////内部类ForwardingNode-BEGIN///////////////////////////////

    /**
     * A node inserted at head of bins during transfer operations.
     * 当进行transfer扩容操作的时候将节点加入到bin的头位置
     */
//  static final class ForwardingNode<K,V> extends Node<K,V> {
      /*
      final Node<K,V>[] nextTable;
        ForwardingNode(Node<K,V>[] tab) {
            super(MOVED, null, null, null);
            this.nextTable = tab;//tab就是当前concurrentHashMap对象存储数据的数组结构
        }
        */

        /*
        Node<K,V> find(int h, Object k) {
            // loop to avoid arbitrarily deep recursion on forwarding nodes
            outer: for (Node<K,V>[] tab = nextTable;;) {
                Node<K,V> e; int n;
                if (k == null || tab == null || (n = tab.length) == 0 ||
                        (e = tabAt(tab, (n - 1) & h)) == null)
                    return null;
                for (;;) {
                    int eh; K ek;
                    if ((eh = e.hash) == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                    if (eh < 0) {
                        if (e instanceof ForwardingNode) {
                            tab = ((ForwardingNode<K,V>)e).nextTable;
                            continue outer;
                        }
                        else
                            return e.find(h, k);
                    }
                    if ((e = e.next) == null)
                        return null;
                }
            }
        }
        */
//  }
    ///////////////////////////内部类ForwardingNode-END///////////////////////////////






    /**
     * 存放node元素的数组，大小是2的整数幂
     */
    //transient volatile Node<K,V>[] table;

    /**
     * 该变量表示table初始化或是扩容的控制变量
     * 当该值为负数时，表示正在进行初始化或是扩容，-1表示正在初始化，-N表示有N个线程正在进行扩容操作，
     * 正数或者0表示尚未进行初始化，表示初始化或是需要扩容的大小
     */
    //private transient volatile int sizeCtl;











    /////////////////////////////////PUT方法-BEGIN////////////////////////////////////////

    /**
     * 该方法为ConcurrentHashMap中put方法源码，思路是根据给出的key值计算hash，找到其在tab中的index，在将数据进行存储即可
     * 源码具体流程如下
     * 首先是检查key/value是否为空，如果为空则抛出异常，并计算key的hash值，然后进行数据操作，直到数据插入成功才返回
     * 数据操作步骤
     * 步骤1:查看tab是否为null，如果为空则进行初始化操作
     * 步骤2:查看tab[index]位置是否为空，如果为空，则将数据直接插入，并退出
     * 步骤3:如果链表正在进行扩容操作，则帮助其进行扩容
     * 步骤4:找到对应的tab[index]节点，对操作进行加锁处理并存储数据，操作如下
     * 步骤4-1:如果该节点是链表节点，则判断该节点是否有相同key值，有则更新，无则新增
     * 步骤4-2:如果首节点是树节点，则新增一个节点
     * 步骤5:检查，如果链表结构中节点数量多于8个，则将链表结构转变为树结构
     *
     */
    /*
    final V putVal(K key, V value, boolean onlyIfAbsent) {

        if (key == null || value == null) throw new NullPointerException();//首先保证key/value不为空

        //计算hash值
        int hash = spread(key.hashCode());//对hashCode进行再散列，算法为(h ^ (h >>> 16)) & HASH_BITS

        int binCount = 0;

        //死循环，直到插入成功
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;

            //操作1:如果tab为空，进行初始化
            //否则，根据hash值计算得到数组索引i，如果tab[i]为空，直接新建节点Node即可。
            //注：tab[i]实质为链表或者红黑树的首节点。
            if (tab == null || (n = tab.length) == 0)

                //tab进行初始化
                tab = initTable();

            //操作2:当前hash指向的tab位置为null
            //取出table位置中相关位置的节点赋值给f
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {//tabAt()是用来计算key所在数组tab[i]的位置

                //如果相关位置为空，则利用cas操作直接存储在指定位置
                if (casTabAt(tab, i, null,new Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }

            //操作3:链表正在进行扩容操作
            //如果tab[i]不为空，且hash值为MOVED(-1)，说明链表正在进行扩容操作
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);     //帮助其扩容

            //操作4:hash值指向的table[i]的节点有值
            else {
                V oldVal = null;

                //针对当前节点f进行加锁操作，进一减小线程冲突
                synchronized (f) {
                    //当且仅当tab[i]为f的时候才能进行如下操作
                    if (tabAt(tab, i) == f) {
                        //操作4-1:该节点是链表节点
                        if (fh >= 0) {

                            //循环获取链表节点(如果出现了则更新value值，如果没有则加入到链表末尾并跳出循环)
                            //无限循环，保证数据的写入
                            binCount = 1;
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;

                                //操作4-1-1:查看当前节点的key值是否相同，如果相同则替换值并退出
                                if (e.hash == hash &&
                                        ((ek = e.key) == key ||
                                                (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;  //替换当前value值
                                    break;
                                }

                                Node<K,V> pred = e;

                                //操作4-1-2:如果e是尾节点，创建节点插入链表尾部并退出
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                            value, null);
                                    break;
                                }
                            }
                        }
                        //操作4-2: 如果首节点为TreeBin类型，说明为红黑树结构，执行putTreeVal操作
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
                            //寻找或新增一个节点
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                    value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                //操作5:链表结构转树结构(链表长度超过8转树)
                if (binCount != 0) {//表示节点插入成功
                    //如果链表节点的数量大于8，那么转换链表结构为红黑树结构。
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        //将当前的ConcurrentHashmap的数量+1
        addCount(1L, binCount);
        return null;
    }*/


    /*static final <K,V> java.util.concurrent.ConcurrentHashMap.Node<K,V> tabAt(java.util.concurrent.ConcurrentHashMap.Node<K,V>[] tab, int i) {
        return (java.util.concurrent.ConcurrentHashMap.Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
    }*/



    /**
     * 操作5:将链表转为红黑树结构
     * Replaces all linked nodes in bin at given index unless table is
     * too small, in which case resizes instead.
     *
     * 1:将Node对象封装成TreeNode对象
     * 2:传递hd，构造红黑树
     */
    /*
    private final void treeifyBin(Node<K,V>[] tab, int index) {
        Node<K,V> b; int n, sc;
        //数组不能为空
        if (tab != null) {
            //如果数组的长度小于指定的长度，执行扩容操作
            if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
                tryPresize(n << 1);
            //如果指定索引位置的节点不为空
            else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
                synchronized (b) {
                    //如果b是指定位置的节点
                    if (tabAt(tab, index) == b) {
                        TreeNode<K,V> hd = null, tl = null;
                        for (Node<K,V> e = b; e != null; e = e.next) {
                            //创建p的treeNode节点
                            TreeNode<K,V> p =
                                    new TreeNode<K,V>(e.hash, e.key, e.val,
                                            null, null);

                            if ((p.prev = tl) == null)
                                hd = p;
                            else
                                tl.next = p;
                            tl = p;
                        }
                        setTabAt(tab, index, new TreeBin<K,V>(hd));
                    }
                }
            }
        }
    }*/


    /////////////////////////////////PUT方法-END////////////////////////////////////////

}
