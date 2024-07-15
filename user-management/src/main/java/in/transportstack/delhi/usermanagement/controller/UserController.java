package in.transportstack.delhi.usermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
public class UserController {

    @GetMapping("/get-user")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Welcome User");
    }
}
