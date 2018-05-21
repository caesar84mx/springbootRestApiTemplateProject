package com.caesar_84mx.springbootRestApi.dto;

//TODO: revise avatarUrl field: might have specific parameters (protocol, host etc.)

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.dto.WithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto extends WithId {
    private String nickname;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private String description;
    private boolean active;
    private LocalDate registered;

    public ProfileDto(User user) {
        super(user.getId());
        this.nickname = user.getNickname();
        this.avatarUrl = user.getAvatarUrl();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.description = user.getDescription();
        this.active = user.isActive();
        this.registered = user.getRegisteredDate();
    }
}

