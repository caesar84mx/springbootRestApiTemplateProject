package com.caesar_84mx.springbootRestApi.service;

import com.caesar_84mx.springbootRestApi.domain.model.Owned;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotFoundException;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotUpdatableException;
import com.caesar_84mx.springbootRestApi.util.exceptions.WrongOwnerException;

import java.util.List;

public interface GenericOwnedService<T extends Owned> {
    T save(T owned, long userId) throws WrongOwnerException;
    T get(long id) throws NotFoundException;
    void update(T owned, long userId) throws NotUpdatableException, WrongOwnerException;
    void delete(long id, long userId) throws NotFoundException;
    void delete(long id) throws NotFoundException;
    List<T> getAll(long userId) throws NotFoundException;
}
