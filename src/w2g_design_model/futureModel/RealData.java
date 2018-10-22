package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 0003.
 */
public class RealData implements Data{

    private String result;//封装实际数据

    /**
     * 获取计算结果方法
     * @return
     */
    @Override
    public String getResult() {
        return result;
    }

    public RealData(String param){
        StringBuffer sb=new StringBuffer();

        sb.append(param);

        //模拟业务执行
        //此处通过线程睡眠的方式
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取真实数据result,等待再次获取数据
        result=sb.toString();
    }

}
