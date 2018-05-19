package com.caesar_84mx.springbootRestApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WithId {
    Long id;

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}

