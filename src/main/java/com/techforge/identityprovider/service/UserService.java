package com.techforge.identityprovider.service;

import com.techforge.identityprovider.dto.AuthProvider;
import com.techforge.identityprovider.dto.Role;
import com.techforge.identityprovider.entity.User;
import com.techforge.identityprovider.entity.UserIdentity;
import com.techforge.identityprovider.exception.UserAlreadyExistException;
import com.techforge.identityprovider.repository.UserIdentityRepository;
import com.techforge.identityprovider.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserIdentityRepository identityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserIdentityRepository identityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String email, String password) throws UserAlreadyExistException {
        Optional<User> existingUser = userRepository.getUserByEmail(email);
        User user;
        UserIdentity identity;
        if(existingUser.isPresent()){
            user = existingUser.get();
            if(Optional.ofNullable(user.getPasswordHash()).isPresent()){
                throw new UserAlreadyExistException("user already exist in the database");
            }else {
                user.setPasswordHash(passwordEncoder.encode(password));
                identity = new UserIdentity(AuthProvider.LOCAL, user);
            }
        }else {
            user = new User();
            user.setEmail(email).setPasswordHash(passwordEncoder.encode(password)).getRoles().add(Role.USER);
            identity = new UserIdentity(AuthProvider.LOCAL, user);
        }
        userRepository.save(user);
        identityRepository.save(identity);
    }

}
