package com.caesar_84mx.springbootRestApi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Owned extends BaseEntity {
    @ManyToOne
    private User user;

    public Owned(Long id, User user) {
        super(id);
        this.user = user;
    }
}
