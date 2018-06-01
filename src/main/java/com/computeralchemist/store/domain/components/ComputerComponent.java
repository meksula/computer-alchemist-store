package com.computeralchemist.store.domain.components;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

/**
 * @Author
 * Karol Meksuła
 * 30-03-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class ComputerComponent extends ResourceSupport {

    public abstract long getProductId();

    @NotNull
    private ComponentType componentType;

    @NotNull
    private String producent;

    @NotNull
    private String model;
    private String description;
    private String urlToResource;
    private double compatibleIndex;
    private long votes;
    private double allPoints;
    private double ratesEvg;

}
