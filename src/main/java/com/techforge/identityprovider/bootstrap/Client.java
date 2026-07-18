package com.techforge.identityprovider.bootstrap;

import java.util.List;

public class Client {

    private String clientId;

    private String clientSecret;

    private List<String> authenticationMethods;

    private List<String> grantTypes;

    private List<String> redirectUris;

    private List<String> scopes;

    private boolean requirePkce;

    private long accessTokenTtl;

    private long refreshTokenTtl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getAuthenticationMethods() {
        return authenticationMethods;
    }

    public void setAuthenticationMethods(List<String> authenticationMethods) {
        this.authenticationMethods = authenticationMethods;
    }

    public List<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public boolean isRequirePkce() {
        return requirePkce;
    }

    public void setRequirePkce(boolean requirePkce) {
        this.requirePkce = requirePkce;
    }

    public long getAccessTokenTtl() {
        return accessTokenTtl;
    }

    public void setAccessTokenTtl(long accessTokenTtl) {
        this.accessTokenTtl = accessTokenTtl;
    }

    public long getRefreshTokenTtl() {
        return refreshTokenTtl;
    }

    public void setRefreshTokenTtl(long refreshTokenTtl) {
        this.refreshTokenTtl = refreshTokenTtl;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", authenticationMethods=" + authenticationMethods +
                ", grantTypes=" + grantTypes +
                ", redirectUris=" + redirectUris +
                ", scopes=" + scopes +
                ", requirePkce=" + requirePkce +
                ", accessTokenTtl=" + accessTokenTtl +
                ", refreshTokenTtl=" + refreshTokenTtl +
                '}';
    }
}
