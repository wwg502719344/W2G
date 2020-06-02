package w2g_concurrent_utils.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by W2G on 2018/12/11.
 */
public class testDemo {
    private final static Map<Integer,String> map=new HashMap<Integer,String>();
    private static int a;
    static{
        a=1;
    }
    public static void main(String[] args){
        //System.out.println(ff());

        a=2;
        System.out.println(a);

    }

    public static int ff(){
        Scanner scanner = new Scanner(System.in);
        String str=scanner.next();
        try {
            int i=Integer.valueOf(str);
            i++;
            System.out.println("try");
            return 2;
        }catch (NumberFormatException e) {
            System.out.println("catch");
            return 1;
        }finally {
            System.out.println("finally");

        }
    }
}
