package cloud.hystrix.project;

public class AService {
    public void getSingleData(int param) {
        System.out.println("AService getSingleData called, param: " + param);
    }

    public void getList() {
        System.out.println("AService getList called");
    }
}
