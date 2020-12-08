package w2g_design_mode;

import java.lang.reflect.Method;

/**
 * 反射类
 */
public class reflectCase {
    public static void main(String[] args) throws Exception {
        Proxy target = new Proxy();
        Method method = Proxy.class.getDeclaredMethod("run");
        method.invoke(target);
    }

    static class Proxy {
        public void run() {
            System.out.println("run");
        }
    }

}
