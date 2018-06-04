package com.computeralchemist.store.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author
 * Karol Meksuła
 * 03-06-2018
 * */

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private UserAuthentication userAuthentication;
    private ApiAuthentication apiAuthentication;
    private User user;

    @Autowired
    public void setUserAuthentication(UserAuthentication userAuthentication, ApiAuthentication apiAuthentication) {
        this.userAuthentication = userAuthentication;
        this.apiAuthentication = apiAuthentication;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicCredentials basicCredentials = new BasicCredentials(authentication.getName(),
                authentication.getCredentials().toString());

        Optional<User> optionalUser = apiAuthentication.postForUser(basicCredentials);

        if (optionalUser.isPresent())
            this.user = optionalUser.get();

        else throw new AuthenticationCredentialsNotFoundException(basicCredentials.getUsername());

        if (userAuthentication.authenticate(user, authentication))
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), getAuthorities());

        else throw new AuthenticationCredentialsNotFoundException(basicCredentials.getUsername());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ConcurrentLinkedQueue<>();

        for (String role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
        }

        return grantedAuthorities;
    }

}
