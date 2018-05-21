package com.caesar_84mx.springbootRestApi.web.rest;

import com.caesar_84mx.springbootRestApi.dto.ApiResponse;
import com.caesar_84mx.springbootRestApi.dto.ProfileDto;
import com.caesar_84mx.springbootRestApi.dto.UserUpdateRequestDto;
import com.caesar_84mx.springbootRestApi.security.UserPrincipal;
import com.caesar_84mx.springbootRestApi.service.UserService;
import com.caesar_84mx.springbootRestApi.util.PasswordUtil;
import com.caesar_84mx.springbootRestApi.util.PrivilegeUtil;
import com.caesar_84mx.springbootRestApi.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> get(@RequestParam(value = "id") long id) {
        var user = userService.get(id);

        return new ResponseEntity<>(new ProfileDto(user), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        var user = UserUtil.updateFromUpdateRequest(userUpdateRequest, userService.get(principal.getId()));
        userService.update(user);

        return new ResponseEntity<>(new ProfileDto(user), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/change-pass")
    public ResponseEntity<?> changePassword(
            @RequestParam(value = "old") String oldPassword,
            @RequestParam(value = "new") String newPassword,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        if (!PasswordUtil.isMatching(oldPassword, principal.getPassword())) {
            return new ResponseEntity<>(new ApiResponse
                    (false, "Old password does not correspond to the current one"),
                    HttpStatus.FORBIDDEN);
        }

        var user = userService.get(principal.getId());
        user.setPassword(newPassword);
        userService.update(user);

        return new ResponseEntity<>(new ApiResponse(true, "Password successfully changed"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserPrincipal principal) {

        if (PrivilegeUtil.hasRole("SUPERUSER", principal.getAuthorities())) {
            return new ResponseEntity<>(new ApiResponse(
                    false,
                    "Your are a superuser, you can't delete yourself! " +
                            "Have a rest, take a cup of coffee, take a couple of weeks of vacations."
            ), HttpStatus.FORBIDDEN);
        }

        userService.delete(principal.getId());

        return new ResponseEntity<>(new ApiResponse(true, "User deleted"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(UserUtil.usersToProfiles(userService.getAll()), HttpStatus.OK);
    }
}