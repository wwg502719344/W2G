import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Main implements Lock{

    //静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer{
        //是否处于占有状态
        protected boolean isHeldExclusively(){
            //方法返回当前状态，判断是否处于占用状态
            return getState()==1;
        }

        //当状态为0 的时候获取锁
        public boolean tryAcquire(int num){
            //当前状态不是独占，设置为独占模式
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁，并将当前状态设置为0
        public boolean tryRelease(int release){
            if (getState()==0){
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }
            return false;
        }

        //返回一个Condition,每个condition都包含一个dondition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    Sync sync=new Sync();


    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


}
