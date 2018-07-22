package w2g_thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by W2G on 2018/7/20 0020.
 * 源码查看：bookmark-J
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread previous=Thread.currentThread();

        for (int i=0;i<10;i++){

            Thread thread=new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous=thread;
        }
        //TimeUnit.SECONDS.sleep(5);
        System.out.print(Thread.currentThread().getName()+"主线程");
    }

    static class Domino implements Runnable{
        private Thread thread;
        public Domino(Thread thread){
            this.thread=thread;
        }

        public void run(){
            try{
                thread.join();
            }catch (Exception e){

            }
            System.out.print(Thread.currentThread().getName()+"子线程");
        }
    }
}
