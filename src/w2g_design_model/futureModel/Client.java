package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 0003.
 * 客户端返回future，实际开启ClientThread去计算数据结果
 *
 */
public class Client {

    public Data request(String param){

        //立即返回futureData
        FutureData futureData=new FutureData();

        //开启ClientThread线程装配realData
        new Thread(() -> {
            //装配realData
            RealData realData=new RealData(param);

            futureData.setRealData(realData);

        }).start();
        return futureData;
    }
}
