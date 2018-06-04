package com.computeralchemist.store.security;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 04-06-2018
 * */

public interface ApiAuthentication {
    Optional<User> postForUser(BasicCredentials basicCredentials);
}
