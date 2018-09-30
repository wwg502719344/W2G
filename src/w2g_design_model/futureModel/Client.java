package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 .
 * 客户端发出请求，创建线程去获取实际数据立即返回future对象
 * 实际开启ClientThread去计算数据结果，在获取实际数据后将数据封装进入future对象
 *
 */
public class Client {

    public Data request(String param){

        //立即返回futureData
        FutureData futureData=new FutureData();

        //需要启动一个新线程
        //开启ClientThread线程装配realData
        new Thread(() -> {
            //装配realData
            //RealData为业务类，传递相关参数过去
            RealData realData=new RealData(param);

            //获取到数据后将数据封装进实体类中
            futureData.setRealData(realData);

        }).start();
        return futureData;
    }
}
