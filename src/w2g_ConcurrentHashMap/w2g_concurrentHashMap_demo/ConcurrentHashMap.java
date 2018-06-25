package w2g_ConcurrentHashMap.w2g_concurrentHashMap_demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by W2G on 2018/6/12.
 */
public class ConcurrentHashMap {

    /**
     * 创建一个新的，默认大小的空的map
     */
    public ConcurrentHashMap() {
    }

    /**
     * 创建一个指定长度的map
     * @param initialCapacity
     */
    public ConcurrentHashMap(int initialCapacity) {
        /*if (initialCapacity < 0)
            throw new IllegalArgumentException();
        根据传入的参数初始化大小
        int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
                MAXIMUM_CAPACITY :
                tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
        this.sizeCtl = cap;*/
    }

    /**
     * Creates a new map with the same mappings as the given map.
     * 创建与给定map相同的map
     * @param m the map
     */
    /*public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
        this.sizeCtl = DEFAULT_CAPACITY;
        putAll(m);
    }*/











    ///////////////////////////内部类Node-BEGIN///////////////////////////////
    /**
     * Node是最核心的内部类，它包装了key-value键值对，所有插入ConcurrentHashMap的数据都包装在这里面。
     * 它与HashMap中的定义很相似，但是有一些差别它对value和next属性设置了volatile同步锁(与JDK7的Segment相同)，
     * 它不允许调用setValue方法直接改变Node的value域，它增加了find方法辅助map.get()方法。
     */
    /**
     *内部类Node源码
     * Node类没有提供修改的方法(setValue会抛出异常，给子类实现)
     * but can be used for read-only traversals used in bulk tasks
     * 但是可以被用作只读遍历在大部分的任务中
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> implements Map.Entry<K,V> {

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

        /**
         * HashMap中Node类的hashCode()方法中的代码为：
         * Objects.hashCode(key) ^ Objects.hashCode(value)
         * 而Objects.hashCode(key)最终也是调用了 key.hashCode()，因此，效果一样。写法不一样罢了
         * @return
         */
        public final int hashCode()   {
            return key.hashCode() ^ val.hashCode();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }


        // 不允许修改value值，HashMap允许
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        /**
         * equals方法
         * @param o
         * @return
         */
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

        /**
         * Virtualized support for map.get(); overridden in subclasses.
         * 辅助get方法，在子类中覆盖
         * @param h
         * @param k
         * @return
         */
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }
    ///////////////////////////内部类Node-END///////////////////////////////
















    ///////////////////////////内部类TreeNode-BEGIN///////////////////////////////

    /**
     * Nodes for use in TreeBins
     * 将节点包装成TreeNode(最后放入TreeBin，再有TreeBin装换成红黑树)
     * @param <K>
     * @param <V>
     */
    static final class TreeNode<K,V> extends Node<K,V> {
        TreeNode<K,V> parent;  // red-black tree links：父节点
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion(应该是没有用了)
        boolean red;

        TreeNode(int hash, K key, V val, Node<K,V> next,
                 TreeNode<K,V> parent) {
            super(hash, key, val, next);
            this.parent = parent;
        }


        Node<K,V> find(int h, Object k) {
            return findTreeNode(h, k, null);
        }

        /**
         * Returns the TreeNode (or null if not found) for the given key
         * 返回给定key值的TreeNode(如果没有找到就返回null)
         * starting at given root.
         * 从给定的root节点开始
         */
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
    }
    ///////////////////////////内部类TreeNode-END///////////////////////////////








    ///////////////////////////内部类TreeBin-BEGIN///////////////////////////////
    /**
     * TreeNodes used at the heads of bins. TreeBins do not hold user
     * keys or values, but instead point to list of TreeNodes and
     * their root. They also maintain a parasitic read-write lock
     * forcing writers (who hold bin lock) to wait for readers (who do
     * not) to complete before tree restructuring operations.
     *
     * TreeNodes被用在桶的顶部，不保持用户keys或者values
     */
    //static final class TreeBin<K,V> extends Node<K,V>

    /**
     * Creates bin with initial set of nodes headed by b.
     * 以b为初始化节点首节点创建bin
     */
    /*TreeBin(TreeNode<K,V> b) {
        super(TREEBIN, null, null, null);
        //b为首节点
        this.first = b;
        TreeNode<K,V> r = null;

        for (TreeNode<K,V> x = b, next; x != null; x = next) {
            next = (TreeNode<K,V>)x.next;
            x.left = x.right = null;
            if (r == null) {
                x.parent = null;
                x.red = false;
                r = x;
            }
            else {
                K k = x.key;
                int h = x.hash;
                Class<?> kc = null;
                for (TreeNode<K,V> p = r;;) {
                    int dir, ph;
                    K pk = p.key;
                    if ((ph = p.hash) > h)
                        dir = -1;
                    else if (ph < h)
                        dir = 1;
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
    ///////////////////////////内部类TreeBin-END///////////////////////////////


















    /////////////////////////////////PUT方法-BEGIN////////////////////////////////////////
    /*
    public V put(K key, V value) {
        return putVal(key, value, false);
    }
    final V putVal(K key, V value, boolean onlyIfAbsent) {

        //如果key或者value为空，抛出异常
        if (key == null || value == null) throw new NullPointerException();

        //对hashCode进行再散列，算法为(h ^ (h >>> 16)) & HASH_BITS
        //计算hash值，2次hash值，用于在指定位置保存查询
        //hash是key的哈希值经过高16位转低16位的int值
        int hash = spread(key.hashCode());

        int binCount = 0;

        //死循环，直到插入成功
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;

            //如果tab为空，进行初始化
            //否则，根据hash值计算得到数组索引i，如果tab[i]为空，直接新建节点Node即可。
            //注：tab[i]实质为链表或者红黑树的首节点。
            if (tab == null || (n = tab.length) == 0)

            //给tab进行初始化,tab = initTable();
            //取出table位置中相关位置的节点赋值给f
            //这里计算节点在table数组的位置的算法是i = (n - 1) & hash
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {

                //如果相关位置为空，则利用cas操作直接存储在指定位置
                if (casTabAt(tab, i, null,new Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }

            //如果tab[i]不为空，且hash值为MOVED(-1)，说明链表正在进行transfer操作
            //MOVED:（forwarding nodes的hash值）、标示位
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);     ////帮助其扩容
            else {
                V oldVal = null;

                //针对当前节点进行加锁操作，进一减小线程冲突
                synchronized (f) {

                    //获取当前位置的node节点，并将f节点附给他
                    //避免多线程，需要重新检查
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {      //链表节点
                            binCount = 1;
                            //循环获取链表节点(如果出现了则更新value值，如果没有则加入到链表末尾并跳出循环)
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                //查看是否有相同的k值，如果有则更新value值
                                if (e.hash == hash &&
                                        ((ek = e.key) == key ||
                                                (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;

                                //插入到列表末尾并退出循环(没理解)
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                            value, null);
                                    break;
                                }
                            }
                        }
                        // 如果首节点为TreeBin类型，说明为红黑树结构，执行putTreeVal操作
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
                //转换为树结构
                if (binCount != 0) {
                    //如果容器数量大于=8，那么转换链表结构为红黑树结构。
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }*/


    /*static final <K,V> java.util.concurrent.ConcurrentHashMap.Node<K,V> tabAt(java.util.concurrent.ConcurrentHashMap.Node<K,V>[] tab, int i) {
        return (java.util.concurrent.ConcurrentHashMap.Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
    }*/
     /**
     * Replaces all linked nodes in bin at given index unless table is
     * 将数组中给定索引位置转换为树
     * too small, in which case resizes instead.
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
