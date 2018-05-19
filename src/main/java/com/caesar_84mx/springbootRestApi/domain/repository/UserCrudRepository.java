package com.caesar_84mx.springbootRestApi.domain.repository;

import com.caesar_84mx.springbootRestApi.domain.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserCrudRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN u.roles WHERE u.email = :accessname OR u.nickname = :accessname")
    Optional<User> getByEmailOrNickname(@Param("accessname") String email);

    Optional<User> findById(Long id);

    @Override
    User getOne(Long id);

    @Transactional
    @Override
    User save(User user);

    @Override
    List<User> findAll(Sort sort);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    long delete(@Param("id") long id);

    int countAllByNickname(String nickname);

    int countAllByEmail(String email);
}
