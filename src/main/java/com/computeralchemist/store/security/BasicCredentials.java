package com.computeralchemist.store.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @Author
 * Karol Meksuła
 * 04-06-2018
 * */

@Getter
class BasicCredentials {
    private String username;
    private String password;

    public BasicCredentials(@JsonProperty("username") String username,
                            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

}
