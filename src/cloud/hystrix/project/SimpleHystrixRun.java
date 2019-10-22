package cloud.hystrix.project;

/**
 * 命令类的完整理解:
 * 创建一个空的命令类，将参数传递，构建一个完整的命令类，在去执行这个命令类
 * 抽象命令接口SimpleHystrixCommand:这个类定义了执行方法的接口
 * 具体的命令对象ASerGetSingleDataCommand:执行具体的命令，实现了抽象命令接口
 * 接受者对象AService实现类:接受命令的对象，真正的命令执行者
 * 传递命令对象SimpleHystrix:持有命令对象，要求命令对象执行请求
 */
public class SimpleHystrixRun {

    public static void main(String[] args) {
        AService aService = new AService();


        SimpleHystrix simpleHystrix = new SimpleHystrix();
        simpleHystrix.setSimpleHystrixCommand(new ASerGetSingleDataCommand(aService, 1));
        simpleHystrix.call();

        simpleHystrix.setSimpleHystrixCommand(new ASerGetListCommand(aService));
        simpleHystrix.call();

        BService bService = new BService();
        simpleHystrix.setSimpleHystrixCommand(new BSerGetSingleDataCommand(bService, 2));
        simpleHystrix.call();

        simpleHystrix.setSimpleHystrixCommand(new BSerGetListCommand(bService));
        simpleHystrix.call();
    }

}
