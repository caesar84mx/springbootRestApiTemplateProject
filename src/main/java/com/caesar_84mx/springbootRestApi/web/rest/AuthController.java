package com.caesar_84mx.springbootRestApi.web.rest;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.dto.user_related.LoginDto;
import com.caesar_84mx.springbootRestApi.dto.user_related.SignupDto;
import com.caesar_84mx.springbootRestApi.dto.user_related.ApiResponse;
import com.caesar_84mx.springbootRestApi.dto.user_related.JwtAuthResponse;
import com.caesar_84mx.springbootRestApi.security.JwtTokenProvider;
import com.caesar_84mx.springbootRestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private JwtTokenProvider tokenProvider;
    private UserService service;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtTokenProvider tokenProvider, UserService service, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody
                                            LoginDto loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getAuthName(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponse(jwt,"Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singup(@Valid @RequestBody SignupDto signupRequest) {
        if (!service.checkEmailAvailable(signupRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email already exists!"), HttpStatus.BAD_REQUEST);
        }

        if (!service.checkNicknameAvailable(signupRequest.getNickname())) {
            return new ResponseEntity<>(new ApiResponse(false, "Nickname already exists!"), HttpStatus.BAD_REQUEST);
        }

        var user = new User(signupRequest);
        var location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("users/{nickname}")
                .buildAndExpand(service.save(user).getNickname())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User successfully registered!"));
    }
}