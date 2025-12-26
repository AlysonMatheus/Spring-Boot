package br.com.Alyson.Controllers;


import br.com.Alyson.data.dto.security.AccountCredentialsDTO;
import br.com.Alyson.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication endpoints")
@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService service;

@Operation(summary = "Authenticates an user and returns a token")
@PostMapping("/signin")
    public ResponseEntity<?> signin(AccountCredentialsDTO credentials){
        if (creddentialsIsInvalid(credentials)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        var token = service.singIn(credentials);
        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    private static boolean creddentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isNotBlank(credentials.getPassword()) ||
                StringUtils.isNotBlank(credentials.getUsername());
    }

}
