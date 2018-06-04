package com.computeralchemist.store.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 04-05-2018
 * */

@Service
public class ApiAuthenticationImpl implements ApiAuthentication {

    @Value(value = "${ca.user.login}")
    private String loginUrl;

    @Override
    public Optional<User> postForUser(BasicCredentials basicCredentials) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(loginUrl, basicCredentials, User.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.of(responseEntity.getBody());
        }

        else throw new AuthenticationCredentialsNotFoundException(basicCredentials.getUsername());
    }

}
