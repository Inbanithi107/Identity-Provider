package com.techforge.identityprovider.repository;

import com.techforge.identityprovider.dto.AuthProvider;
import com.techforge.identityprovider.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, UUID> {

    @Query("select i from UserIdentity i join fetch i.user u join fetch u.roles where i.provider = :provider and i.providerUserId = :id")
    Optional<UserIdentity> getIdentityByProviderAndProviderId(@Param("provider") AuthProvider provider, @Param("id") String providerId);

}
