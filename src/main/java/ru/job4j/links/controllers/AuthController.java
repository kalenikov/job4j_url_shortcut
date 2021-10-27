package ru.job4j.links.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.links.dto.LoginRequest;
import ru.job4j.links.dto.LoginResponse;
import ru.job4j.links.dto.RegRequest;
import ru.job4j.links.dto.RegResponse;
import ru.job4j.links.security.JwtUtils;
import ru.job4j.links.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateToken(auth);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> reg(@Valid @RequestBody RegRequest regRequest) {
        var user = userService.getBySite(regRequest.getSite());
        return ResponseEntity.ok(
                RegResponse.of(
                        user.isNew(),
                        user.getUsername(),
                        user.getPasswordPlainText()
                ));
    }
}
