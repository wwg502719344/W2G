package cloud;

/**
 * Created by W2G on 2019/10/30.
 * 解析类DiscoveryClient
 * 该类位于maven中eureka项目下discovery包中
 */
public class Eureka_Source {

    /*
    boolean register() throws Throwable {
        logger.info("DiscoveryClient_{}: registering service...", this.appPathIdentifier);

        EurekaHttpResponse httpResponse;
        try {
            httpResponse = this.eurekaTransport.registrationClient.register(this.instanceInfo);
        } catch (Exception var3) {
            logger.warn("DiscoveryClient_{} - registration failed {}", new Object[]{this.appPathIdentifier, var3.getMessage(), var3});
            throw var3;
        }

        if(logger.isInfoEnabled()) {
            logger.info("DiscoveryClient_{} - registration status: {}", this.appPathIdentifier, Integer.valueOf(httpResponse.getStatusCode()));
        }

        return httpResponse.getStatusCode() == Status.NO_CONTENT.getStatusCode();
    }*/

}
