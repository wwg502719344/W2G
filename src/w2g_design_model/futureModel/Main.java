package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 0003.
 * future设计模式的理念是通过预先返回
 * 该类只是简单表达了future设计模式的思想，实际并没有调用future源码，没有采用futureTask类
 */
public class Main {

    public static void main(String args[]){
        Client client=new Client();

        //调用client发送请求
        Data data=client.request("Hello Future!");

        System.out.println("请求完毕！");

        try {
            //模拟处理其他业务  
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //调用实际返回的数据
        System.out.println("真实数据：" + data.getResult());

    }
}
