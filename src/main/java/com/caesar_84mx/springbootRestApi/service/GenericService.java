package com.caesar_84mx.springbootRestApi.service;

import com.caesar_84mx.springbootRestApi.domain.model.BaseEntity;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotFoundException;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotUpdatableException;

import java.util.List;

public interface GenericService<T extends BaseEntity> {
    T save(T entity);
    T get(long id) throws NotFoundException;
    void update(T entity) throws NotUpdatableException;
    void delete(long id) throws NotFoundException;
    List<T> getAll();
}
