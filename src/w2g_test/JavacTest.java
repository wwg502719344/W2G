package w2g_test;

/**
 * Created by W2G on 2020/1/16.
 * 字节码指令集
 *  0: ldc           #2                  // String java
 *  2: astore_1
 *  3: ldc           #2                  // String java
 *  5: astore_2
 *  6: ldc           #2                  // String java
 *  8: astore_3
 *  9: return
 *
 *  ldc:从常量池中获取值,JVM采用ldc指令将常量压入栈中
 *  astore_1:对值进行保存
 *  获取常量池中的字符串
 *
 *  生成字节码指令方式
 *  https://blog.csdn.net/en_joker/article/details/88389643
 *
 *  更多
 *  https://www.jianshu.com/p/2f209af80f84
 */
public class JavacTest {

    public static void main(String args[]){
        String a = "hello ";
        String b = "world";
        String c = a + b;
        String d = "hello world";
    }
}
