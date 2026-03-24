package com.techforge.identityprovider.repository;

import com.techforge.identityprovider.entity.Jwk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JwkRepository extends JpaRepository<Jwk, UUID> {

}
