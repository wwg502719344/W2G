package w2g_thread_pool.demo;

/**
 * Created by W2G on 2018/9/13.
 */
public class futureTask_demo {

        public static void main(String[] args) {


            /**
             * P1:future设计模式中FutureTask的实现场景
             */
            /*FutureTask<String> futureTask = new FutureTask<>(new RealData("Hello"));

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            //此处注意，传入的实际是FutureTask对象,但是executorService接收的是Runnable/callable对象
            //并且executorService还会将他们封装到FutureTask当中去，尼玛的搞不明白了，下面的也是这个情况P1-1
            //线程池QA-Q11，解惑
            executorService.execute(futureTask);

            System.out.println("请求完毕！");

            try {
                Thread.sleep(2000);
                System.out.println("这里经过了一个2秒的操作！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("真实数据：" + futureTask.get());
            executorService.shutdown();*/




            /**
             * P2
             * 利用futureTask和executorService实现的异步任务调用，可以用多线程的方式提交计算任务，主线程可以继续执行自己的程序。。。
             * 可以通过get方法查看计算的结果
             */
            /*
            FutureTaskForMultiCompute inst=new FutureTaskForMultiCompute();
            // 创建任务集合
            List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
            // 创建线程池(固定的线程池大小)
            ExecutorService exec = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 10; i++) {
                // 传入Callable对象创建FutureTask对象
                FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i, ""+i));
                taskList.add(ft);
                // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
                // P1-1:这里不明白，ft已经是FutureTask类了，在submit中会再次被构造成FutureTask，这不就有问题吗
                // 线程池QA-Q11，解惑
                exec.submit(ft);
            }

            System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");

            // 开始统计各计算线程计算结果
            Integer totalResult = 0;
            for (FutureTask<Integer> ft : taskList) {
                try {
                    //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                    totalResult = totalResult + ft.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // 关闭线程池
            exec.shutdown();
            System.out.println("多任务计算后的总结果是:" + totalResult);

        }

        private class ComputeTask implements Callable<Integer> {

            private Integer result = 0;
            private String taskName = "";

            public ComputeTask(Integer iniResult, String taskName){
                result = iniResult;
                this.taskName = taskName;
                System.out.println("生成子线程计算任务: "+taskName);
            }

            public String getTaskName(){
                return this.taskName;
            }

            @Override
            public Integer call() throws Exception {
                // TODO Auto-generated method stub

                for (int i = 0; i < 100; i++) {
                    result =+ i;
                }
                // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
                Thread.sleep(5000);
                System.out.println("子线程计算任务: "+taskName+" 执行完成!");
                return result;
            }
        }
        */
    }

}
