package com.caesar_84mx.springbootRestApi.domain.model;
//TODO: revise this class: might have specific fields, depending on your needs
//TODO: revise avatarUrl field: might have specific parameters (protocol, host etc.)

import com.caesar_84mx.springbootRestApi.dto.SignupDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Email
    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @NotNull
    @NotBlank
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @URL
    @Column(name = "avatar_url")
    private String avatarUrl;

    @NotNull
    @NotBlank
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean active;

    @NonNull
    @Column(name = "registered_date", updatable = false, columnDefinition = "timestamp default now()")
    private LocalDate registeredDate;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    Set<UserRole> roles;

    public User(Long id,
                @Email String email,
                @NotNull @NotBlank String password,
                @URL String avatarUrl,
                @NotNull @NotBlank String nickname,
                String firstName,
                String lastName,
                String description,
                boolean active,
                LocalDate registeredDate,
                Set<UserRole> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.active = active;
        this.registeredDate = registeredDate;
        this.roles = roles;
    }

    public User(SignupDto signupDto) {
        this(
                signupDto.getEmail(),
                signupDto.getPassword(),
                signupDto.getAvatarUrl(),
                signupDto.getNickname(),
                signupDto.getFirstName(),
                signupDto.getLastName(),
                signupDto.getDescription(),
                true,
                LocalDate.now(),
                Collections.singleton(UserRole.USER)
        );
    }
}
