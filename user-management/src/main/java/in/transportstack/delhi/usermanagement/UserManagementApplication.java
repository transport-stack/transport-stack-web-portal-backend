package in.transportstack.delhi.usermanagement;

import in.transportstack.delhi.core.CoreApplication;
import in.transportstack.delhi.sharedconfig.SharedConfigApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        CoreApplication.class,
        SharedConfigApplication.class
})
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

}
