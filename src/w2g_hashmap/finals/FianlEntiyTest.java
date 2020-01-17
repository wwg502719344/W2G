package w2g_hashmap.finals;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by W2G on 2018/1/16.
 * 1:final修饰的集合中的变量是可以修改的，不是固定的,初始化后也是可以继续添加的
 * 2:final集合中元素的属性也是可以修改的，只是引用不能修改
 */
public class FianlEntiyTest {

    private static final Set<FianlEntiy> f=new HashSet<FianlEntiy>();

    public FianlEntiyTest(){
        FianlEntiy fianlEntiy=new FianlEntiy();
        fianlEntiy.setA("kk");
        f.add(fianlEntiy);
    }
    public static void main(String args[]){
        FianlEntiyTest f1=new FianlEntiyTest();

        FianlEntiy fianlEntiy1=new FianlEntiy();
        fianlEntiy1.setA("a1");

        FianlEntiy fianlEntiy2=new FianlEntiy();
        fianlEntiy2.setA("a2");

        f.add(fianlEntiy1);

        f.add(fianlEntiy2);
        fianlEntiy1.setA("a3");//
        System.out.println(f.size());
    }
}
