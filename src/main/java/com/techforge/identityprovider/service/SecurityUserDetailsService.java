package com.techforge.identityprovider.service;

import com.techforge.identityprovider.dto.AuthProvider;
import com.techforge.identityprovider.dto.SecurityUser;
import com.techforge.identityprovider.entity.User;
import com.techforge.identityprovider.entity.UserIdentity;
import com.techforge.identityprovider.repository.UserIdentityRepository;
import com.techforge.identityprovider.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService, OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRepository userRepository;

    private final UserIdentityRepository identityRepository;

    private final UserIdentityService userIdentityService;

    public SecurityUserDetailsService(UserRepository userRepository, UserIdentityRepository identityRepository, UserIdentityService userIdentityService) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.userIdentityService = userIdentityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.getUserWithRoles(username);
        if(existingUser.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }

        return new SecurityUser(existingUser.get());

    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oAuth2User = new OidcUserService().loadUser(userRequest);
        AuthProvider provider = AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        System.out.println("dkbckudfbakudbkaduhkuasbfaukgsbkuahdiaufbciahefuabfisuefasuhfuiashf");
        User user = identityRepository.getIdentityByProviderAndProviderId(provider, providerId)
                .map(UserIdentity::getUser)
                .orElseGet(()-> userIdentityService.registerUser(provider, providerId, email));

        return new SecurityUser(user, oAuth2User);
    }
}
