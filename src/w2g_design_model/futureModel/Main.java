package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 0003.
 */
public class Main {

    public static void main(String args[]){
        Client client=new Client();

        Data data=client.request("Hello Future!");

        System.out.println("请求完毕！");

        try {
            //模拟处理其他业务
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("真实数据：" + data.getResult());

    }
}
