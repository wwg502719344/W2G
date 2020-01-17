package w2g_hashmap.finals;


/**
 * Created by W2G on 2018/1/16.
 * 对象cache只能指向另一个对象,无法去修改当前对象的值，
 * 因为cache的属性是final类型的，初始化后无法进行修改
 */
public class VolatileCached {

    private static volatile OneValueCache cache=new OneValueCache("a","a");

    public static void main(String args[]){
        String factors=cache.getFactors("a");
        if (factors!=null){
            //
        }
        System.out.println("111");
    }
}
