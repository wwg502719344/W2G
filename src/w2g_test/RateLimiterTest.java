//package w2g_test;
//
//import com.google.common.util.concurrent.RateLimiter;
//
///**
// * Created by W2G on 2019/8/21.
// * rateLimiter实现限速器，通过acquire阻塞方式获取资源并计算获取资源所需要的时间，
// * 以此来控制并发强度
// */
//public class RateLimiterTest {
//
//    public static void main(String[] args){
//        RateLimiter limiter = RateLimiter.create(1);
//
//        for(int i = 1; i < 10; i = i + 2 ) {
//            double waitTime = limiter.acquire(i);
//            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
//        }
//    }
//}
