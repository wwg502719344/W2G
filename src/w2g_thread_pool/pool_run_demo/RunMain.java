package w2g_thread_pool.pool_run_demo;

/**
 * Created by W2G on 2018/9/27 0027.
 * 执行入口类，创建线程池对象类，调用方法启动线程池执行线程，task类为实现了runnable接口的实现类，业务类
 */
public class RunMain {
    public static void main(String[] args) {
        Server server = new Server();
        for(int i = 0; i < 10; i++) {
            Task task = new Task("Task " + i);
            server.executeTask(task);
        }
        server.endServer();
    }
}
