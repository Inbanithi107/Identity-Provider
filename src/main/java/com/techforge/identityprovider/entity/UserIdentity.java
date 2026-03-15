package com.techforge.identityprovider.entity;

import com.techforge.identityprovider.dto.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table
public class UserIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider provider;

    private String providerUserId;

    @ManyToOne
    @JoinColumn
    private User user;

    public UUID getId() {
        return id;
    }

    public UserIdentity setId(UUID id) {
        this.id = id;
        return this;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public UserIdentity setProvider(AuthProvider provider) {
        this.provider = provider;
        return this;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public UserIdentity setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserIdentity setUser(User user) {
        this.user = user;
        return this;
    }

    public UserIdentity(AuthProvider provider, User user) {
        this.provider = provider;
        this.user = user;
    }

    public UserIdentity(AuthProvider provider, String providerUserId) {
        this.provider = provider;
        this.providerUserId = providerUserId;
    }
}
