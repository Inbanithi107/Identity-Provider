package com.techforge.identityprovider.repository;

import com.techforge.identityprovider.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, UUID> {
}
