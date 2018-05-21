package com.caesar_84mx.springbootRestApi.service.implementations;

import com.caesar_84mx.springbootRestApi.domain.repository.UserCrudRepository;
import com.caesar_84mx.springbootRestApi.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private UserCrudRepository repository;

    @Autowired
    public CustomUserDetailsService(UserCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String authData) throws UsernameNotFoundException {
        log.trace("[{}] - Loading user auth name = {}", this.getClass().getSimpleName(), authData);
        var user = repository.getByEmailOrNickname(authData).orElseThrow(() ->
                new UsernameNotFoundException("User authData = " + authData + " not found!"));

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        log.trace("[{}] - Loading user id = {}", this.getClass().getSimpleName(), id);
        var user = repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id = " + id + " not found!"));

        return UserPrincipal.create(user);
    }
}
