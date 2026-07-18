package com.techforge.identityprovider.util;

import com.techforge.identityprovider.bootstrap.Client;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

public class ClientBuilder {

    public static RegisteredClient toRegisteredClient(Client client, PasswordEncoder passwordEncoder){

        RegisteredClient.Builder builder = RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId());

        if (client.getClientSecret() != null && !client.getClientSecret().isBlank()) {
            builder.clientSecret(passwordEncoder.encode(client.getClientSecret()));
        }

        client.getAuthenticationMethods().forEach(method ->
                builder.clientAuthenticationMethod(new ClientAuthenticationMethod(method)));

        client.getGrantTypes().forEach(grant ->
                builder.authorizationGrantType(new AuthorizationGrantType(grant)));

        client.getRedirectUris().forEach(builder::redirectUri);

        client.getScopes().forEach(builder::scope);

        builder.clientSettings(ClientSettings.builder()
                .requireProofKey(client.isRequirePkce())
                .build());

        builder.tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofSeconds(client.getAccessTokenTtl()))
                .refreshTokenTimeToLive(Duration.ofSeconds(client.getRefreshTokenTtl()))
                .build());

        return builder.build();

    }

}
