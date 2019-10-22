package w2g_ConcurrentHashMap;

/**
 * Created by W2G on 2018/11/29.
 */
public class TestNode {
    public static void main(String args[]){
        int k=1;

        //这里是表示int a1
        for (int a =k ,a1;a!=0;a=a1){
            System.out.print(a);
            a1=a;

            a1++;
            if (a==5){
                System.out.print("6");
                break;
            }
        }
    }
}
