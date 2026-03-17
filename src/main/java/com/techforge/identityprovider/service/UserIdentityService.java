package com.techforge.identityprovider.service;

import com.techforge.identityprovider.dto.AuthProvider;
import com.techforge.identityprovider.dto.Role;
import com.techforge.identityprovider.entity.User;
import com.techforge.identityprovider.entity.UserIdentity;
import com.techforge.identityprovider.repository.UserIdentityRepository;
import com.techforge.identityprovider.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserIdentityService {

    private final UserIdentityRepository identityRepository;

    private final UserRepository userRepository;

    public UserIdentityService(UserIdentityRepository identityRepository, UserRepository userRepository) {
        this.identityRepository = identityRepository;
        this.userRepository = userRepository;
    }

    public void createIdentity(UserIdentity identity){
        identityRepository.save(identity);
    }

    public User registerUser(AuthProvider provider, String providerId, String email){
        User user = userRepository.getUserWithRoles(email)
                .orElseGet(()-> {
                    User saveUser = new User();
                    saveUser.setEmail(email).getRoles().add(Role.USER);
                    return userRepository.save(saveUser);
                });
        identityRepository.save(new UserIdentity(provider, providerId).setUser(user));
        return user;
    }
}
