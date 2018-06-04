package com.computeralchemist.store.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 04-06-2018
 * */

@Service
public class UserAuthenticationImpl implements UserAuthentication {
    private PasswordEncoder passwordEncoder;

    public UserAuthenticationImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean authenticate(User user, Authentication authentication) {
        boolean matches = passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword());
        return matches && user.getUsername().equals(authentication.getName());
    }
}
