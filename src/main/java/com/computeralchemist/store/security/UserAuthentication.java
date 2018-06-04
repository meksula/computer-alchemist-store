package com.computeralchemist.store.security;

import org.springframework.security.core.Authentication;

/**
 * @Author
 * Karol Meksuła
 * 04-06-2018
 * */

public interface UserAuthentication {
    boolean authenticate(User user, Authentication authentication);
}
