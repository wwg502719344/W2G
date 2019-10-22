package cloud.hystrix.project;

public interface SimpleHystrixCommand {
    void execute();
    void fallback();
}
