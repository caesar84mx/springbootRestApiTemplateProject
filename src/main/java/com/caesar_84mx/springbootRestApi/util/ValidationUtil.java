package com.caesar_84mx.springbootRestApi.util;

import com.caesar_84mx.springbootRestApi.domain.model.BaseEntity;
import com.caesar_84mx.springbootRestApi.domain.model.Owned;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotFoundException;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotUpdatableException;
import com.caesar_84mx.springbootRestApi.util.exceptions.WrongOwnerException;

public class ValidationUtil {
    private ValidationUtil() {}

    public static <T> T checkNotFound(T entity, String msg) throws NotFoundException {
        if (entity == null) {
            throw new NotFoundException(msg);
        }

        return entity;
    }

    public static void checkNotFoundWithId(boolean found, long id) throws NotFoundException {
        if (!found) {
            throw new NotFoundException("Not found entity id = " + id);
        }
    }

    public static <T extends Owned> T checkOwner(T entity, long userId) throws WrongOwnerException {
        if (entity.getUser().getId() != userId) {
            throw new WrongOwnerException("This entity does not belong to the user id = " + userId);
        }
        return entity;
    }

    public static <T extends BaseEntity> void checkNew (T entity) throws NotUpdatableException {
        if (entity.isNew()) {
            throw new NotUpdatableException("Failed to update. Post must not be new");
        }
    }
}