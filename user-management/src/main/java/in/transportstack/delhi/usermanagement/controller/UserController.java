package in.transportstack.delhi.usermanagement.controller;

import in.transportstack.delhi.usermanagement.dto.LoginUserRequestDto;
import in.transportstack.delhi.usermanagement.dto.LoginUserResponseDto;
import in.transportstack.delhi.usermanagement.dto.RegisterUserRequestDto;
import in.transportstack.delhi.usermanagement.dto.RegisterUserResponseDto;
import in.transportstack.delhi.usermanagement.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequestDto loginUserRequestDto) {
        LoginUserResponseDto loginUserResponseDto = authenticationService.loginUser(loginUserRequestDto);
        if (loginUserResponseDto != null) {
            return ResponseEntity.ok(loginUserResponseDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<RegisterUserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto)
            throws Exception {
        RegisterUserResponseDto registerUserResponseDto = authenticationService.registerUser(registerUserRequestDto);
        return ResponseEntity.ok(registerUserResponseDto);
    }

    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER')")
    public String securedEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return "This is a secured endpoint!" + jwt.getClaims().toString() + jwt.getHeaders().toString();
    }
}
