package com.caesar_84mx.springbootRestApi;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.domain.model.UserRole;
import com.caesar_84mx.springbootRestApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class SpringbootRestApiApplication implements ApplicationRunner {
    @Autowired
    private UserService service;

    @Value("${app.superuser.email}")
    private String suEmail;
    @Value("${app.superuser.login}")
    private String suLogin;
    @Value("${app.superuser.password}")
    private String suPassword;
    @Value("${app.superuser.fname}")
    private String suFname;
    @Value("${app.superuser.lname}")
    private String suLname;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.save(new User(
                suEmail,
                suPassword,
                "http://your.avatar.url",
                suLogin,
                suFname,
                suLname,
                "Superuser. A God in here.",
                true,
                LocalDate.now(),
                Set.of(UserRole.USER, UserRole.ADMIN, UserRole.SUPERUSER)
        ));
    }
}
