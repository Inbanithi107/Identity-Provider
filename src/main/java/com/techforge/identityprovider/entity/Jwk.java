package com.techforge.identityprovider.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table
public class Jwk {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Lob
    private String keyString;

    public UUID getId() {
        return id;
    }

    public Jwk setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getKeyString() {
        return keyString;
    }

    public Jwk setKeyString(String keyString) {
        this.keyString = keyString;
        return this;
    }
}
