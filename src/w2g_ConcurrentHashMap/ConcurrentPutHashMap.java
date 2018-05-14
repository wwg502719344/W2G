package w2g_ConcurrentHashMap;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by W2G on 2018/5/14 0014.
 * 验证并发状态下的hashMap的put操作(会形成环形链表)，具体原因有待学习，java1.8已解决形成环链的情况
 * 结论:会造成死锁的状况
 *
 * hashMap内部维护了一个数组和链表，数组存放的是对应的node类，node存放了key、value（键值对），next(指向下一个Entry)
 * 插入元素位置是通过计算哈希值确定的，哈希值相同则存放在对应的链表中
 */
public class ConcurrentPutHashMap {

    public static void main(String[] args) throws InterruptedException {
        final HashMap<String,String> map=new HashMap<String,String>();

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10000;i++){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
    }
}
