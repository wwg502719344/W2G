package w2g_design_model.futureModel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by W2G on 2018/9/3 0003.
 */
public class FutureData implements Data {


    private RealData realData;

    private boolean isReady=false;//如果已经获取数据此处为true

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    /**
     * 获取实际数据
     * @return
     */
    @Override
    public String getResult()
    {
        //如果尚未成功获取数据，则当前线程加入等待队列
        while (!isReady){
            try {
                lock.lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        return realData.getResult();
    }

    public void setRealData(RealData realData){
        lock.lock();
        if (isReady){
            return;
        }

        //装载实际数据对象
        this.realData = realData;
        isReady = true;
        condition.signal();
        lock.unlock();
    }
}
