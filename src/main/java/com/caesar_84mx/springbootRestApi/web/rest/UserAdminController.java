package com.caesar_84mx.springbootRestApi.web.rest;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.domain.model.UserRole;
import com.caesar_84mx.springbootRestApi.dto.ApiResponse;
import com.caesar_84mx.springbootRestApi.dto.ProfileDto;
import com.caesar_84mx.springbootRestApi.dto.UserUpdateRequestDto;
import com.caesar_84mx.springbootRestApi.security.UserPrincipal;
import com.caesar_84mx.springbootRestApi.service.UserService;
import com.caesar_84mx.springbootRestApi.util.PrivilegeUtil;
import com.caesar_84mx.springbootRestApi.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "/api/admin/users")
public class UserAdminController {
    private UserService userService;
    private ResponseEntity<?> nepResponse = new ResponseEntity<>(
            new ApiResponse(false, "Not enough privilege!"),
            HttpStatus.FORBIDDEN);

    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAnother(
            @PathVariable("id") long id,
            @Valid @RequestBody UserUpdateRequestDto updateUserRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        var user = UserUtil.updateFromUpdateRequest(updateUserRequest, userService.get(id));

        if (!hasEnoughPrivilege(principal, user))
            return nepResponse;

        userService.update(user);

        return new ResponseEntity<>(new ProfileDto(user), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{id}/change-pass")
    public ResponseEntity<?> changeAnotherPassword(
            @RequestParam("pass") String password,
            @PathVariable("id") long id,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        var user = userService.get(id);

        if (!hasEnoughPrivilege(principal, user))
            return nepResponse;

        user.setPassword(password);
        userService.update(user);

        return new ResponseEntity<>(new ApiResponse(
                true,
                String.format("User's id = %d password successfully changed by %s", id, principal.getUsername())),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteAnother(
            @PathVariable("id") long id,
            @AuthenticationPrincipal UserPrincipal principal) {
        var user = userService.get(id);

        if (!hasEnoughPrivilege(principal, user))
            return nepResponse;

        userService.delete(id);

        return new ResponseEntity<>(new ApiResponse(true, "User id = " + id + " deleted"), HttpStatus.OK);
    }

    @PutMapping(value = "/active-status")
    public ResponseEntity<?> setActiveStatus(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "active") boolean active,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        var user = userService.get(id);

        if (!hasEnoughPrivilege(principal, user))
            return nepResponse;

        userService.setActive(id, active);

        return new ResponseEntity<>(new ApiResponse(true, "User active status set to " + active), HttpStatus.OK);
    }

    private boolean hasEnoughPrivilege(UserPrincipal principal, User user) {
        return user.getRoles().contains(UserRole.ADMIN) && PrivilegeUtil.hasRole("SUPERUSER", principal.getAuthorities());
    }
}
