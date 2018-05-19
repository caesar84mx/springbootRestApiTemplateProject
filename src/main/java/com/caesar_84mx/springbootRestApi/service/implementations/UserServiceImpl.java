package com.caesar_84mx.springbootRestApi.service.implementations;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import com.caesar_84mx.springbootRestApi.domain.repository.UserCrudRepository;
import com.caesar_84mx.springbootRestApi.service.UserService;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotFoundException;
import com.caesar_84mx.springbootRestApi.util.exceptions.NotUpdatableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.caesar_84mx.springbootRestApi.util.UserUtil.prepareToSave;
import static com.caesar_84mx.springbootRestApi.util.ValidationUtil.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserCrudRepository repository;

    @Autowired
    public UserServiceImpl(UserCrudRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public User save(User user) {
        log.trace("[{}] - Saving user {}", this.getClass().getSimpleName(), user);
        return repository.save(prepareToSave(user));
    }

    @Override
    public User get(long id) throws NotFoundException {
        log.trace("[{}] - Getting user id = ", this.getClass().getSimpleName(), id);

        return checkNotFound(repository.getOne(id), "User id = " + id + " not found");
    }

    @Secured({"USER", "ADMIN", "SUPERUSER"})
    @Transactional
    @Override
    public void update(User user) throws NotUpdatableException {
        log.trace("[{}] - Updating user {}", this.getClass().getSimpleName(), user);
        if (user.isNew()) {
            throw new NotUpdatableException("Failed to update. Reason: user is new");
        }

        Assert.notNull(user, "User can not be null");
        repository.save(user);
    }

    @Secured({"ADMIN", "SUPERUSER"})
    @Transactional
    @Override
    public void delete(long id) throws NotFoundException {
        log.trace("[{}] - Deleting user id = {}", this.getClass().getSimpleName(), id);
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public List<User> getAll() {
        log.trace("[{}] - Getting users list", this.getClass().getSimpleName());

        return repository.findAll(new Sort(Sort.Direction.DESC, "registered_date"));
    }

    @Override
    public boolean checkEmailAvailable(String email) {
        log.trace("[{}] - Checking email availability", this.getClass().getSimpleName());

        return repository.countAllByEmail(email) == 0;
    }

    @Override
    public boolean checkNicknameAvailable(String nickname) {
        log.trace("[{}] - Checking nickname availability", this.getClass().getSimpleName());

        return repository.countAllByNickname(nickname) == 0;
    }

    @Secured({"ADMIN", "SUPERUSER"})
    @Transactional
    @Override
    public void setActive(long id, boolean active) throws NotFoundException {
        log.trace("[{}] - Setting user's (id = {}) active status to {}", this.getClass().getSimpleName(), id, active);
        var user = checkNotFound(get(id), "Not found user id = " + id);
        user.setActive(active);
        repository.save(user);
    }
}