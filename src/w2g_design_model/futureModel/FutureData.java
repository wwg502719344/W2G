package w2g_design_model.futureModel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by W2G on 2018/9/3 0003.
 */
public class FutureData implements Data {


    private RealData realData;

    private boolean isReady=false;

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    @Override
    public String getResult()
    {
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

        this.realData = realData;
        isReady = true;
        condition.signal();
        lock.unlock();
    }
}
