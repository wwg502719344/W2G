package w2g_hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by W2G on 2018/5/31.
 */
public class hashMapSource {

    public static void main(String[] args){
        HashMap<String,String> hashMap=new HashMap<>();

        //添加方法
        hashMap.put("1","test");

        //遍历方法
        Set<String> keys=hashMap.keySet();
        for (String key:keys){
            System.out.print(key+"="+hashMap.get(key));
        }

        Iterator iter=hashMap.keySet().iterator();
        while(iter.hasNext()){
            Object key=iter.next();
        }
        hashMap.get("1");
        hashMap.remove("1");
    }

}
