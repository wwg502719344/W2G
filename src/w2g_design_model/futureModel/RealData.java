package w2g_design_model.futureModel;

/**
 * Created by W2G on 2018/9/3 0003.
 */
public class RealData implements Data{

    private String result;


    @Override
    public String getResult() {
        return result;
    }

    public RealData(String param){
        StringBuffer sb=new StringBuffer();
        sb.append(param);

        //模拟业务执行
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取真实数据result
        result=sb.toString();
    }

}
