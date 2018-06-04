package com.computeralchemist.store.security;

import com.computeralchemist.store.domain.order.address.Address;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    @Id
    private long id;

    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int bornyear;
    private Address address;

    private String[] roles;

    public User() {}

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.bornyear = user.getBornyear();
        this.roles = user.getRoles();
        this.address = user.getAddress();
    }

}
