package com.techforge.identityprovider.repository;

import com.techforge.identityprovider.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u where u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);

    @Query("select u from User u join fetch u.roles where u.email = :email")
    Optional<User> getUserWithRoles(@Param("email") String email);

}
