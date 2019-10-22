package cloud.hystrix.project;

public class SimpleHystrix {

    private SimpleHystrixCommand simpleHystrixCommand;

    public void setSimpleHystrixCommand(SimpleHystrixCommand simpleHystrixCommand) {
        this.simpleHystrixCommand = simpleHystrixCommand;
    }

    public void call() {
        simpleHystrixCommand.execute();
    }

}
