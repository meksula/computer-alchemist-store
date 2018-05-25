package com.computeralchemist.store.domain.store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@Entity
@Getter
@Setter
@Table(name = "store")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @NotNull
    private long userId;

    @NotNull
    private String username;

    @NotNull
    @Size(max = 30)
    private String storeName;

    @Email
    private String storeEmail;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String accountNumber;

    @Length(max = 3000)
    private String description;

}
