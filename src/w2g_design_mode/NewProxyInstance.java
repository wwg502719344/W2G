package w2g_design_mode;

import java.lang.reflect.Proxy;

/**
 * Created by W2G on 2019/10/12.
 * 创建动态代理类源码
 */
public class NewProxyInstance {

    //动态代理类
    Proxy a;


    /**
     * 生成代理对象方法
     * loader:类加载器
     * interfaces:加载
     */
    /*public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
            throws IllegalArgumentException
    {
        Objects.requireNonNull(h);

        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }
        //生成代理类对象
        Class<?> cl = getProxyClass0(loader, intfs);

        //使用指定的调用处理程序获取代理类的构造函数对象
        try {
            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }

            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            //如果Class作用域为私有，通过 setAccessible 支持访问
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            //获取Proxy Class构造函数，创建Proxy代理实例。
            return cons.newInstance(new Object[]{h});
        } catch (IllegalAccessException|InstantiationException e) {
            throw new InternalError(e.toString(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new InternalError(t.toString(), t);
            }
        } catch (NoSuchMethodException e) {
            throw new InternalError(e.toString(), e);
        }
    }*/
}
