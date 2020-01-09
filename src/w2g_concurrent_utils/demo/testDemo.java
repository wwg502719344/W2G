package w2g_concurrent_utils.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by W2G on 2018/12/11.
 */
public class testDemo {
    private final static Map<Integer,String> map=new HashMap<Integer,String>();

    public static void main(String[] args){
        map.put(1,"1");
        map.put(2,"2");
        System.out.println(map.get(2));
    }
}
