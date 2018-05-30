package com.computeralchemist.store.domain.store.components.ram;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 05-04-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RamParameters implements Serializable {
    private String memoryType;
    private String cooler;
    private int modules;
    private int capacityGb;
    private double frequency;
    private String socketType;
}
