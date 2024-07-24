package in.transportstack.delhi.runnerservice;

import in.transportstack.delhi.core.CoreApplication;
import in.transportstack.delhi.gatewayservice.GatewayServiceApplication;
import in.transportstack.delhi.usermanagement.UserManagementApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        GatewayServiceApplication.class,
        UserManagementApplication.class,
        CoreApplication.class
})
public class RunnerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunnerServiceApplication.class, args);
    }

}
