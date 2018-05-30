package com.computeralchemist.store.domain.store.components.supply;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 07-08-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PowerSupplyParameters implements Serializable {
    private String standard;
    private int power;
    private String pfc;
    private String cooler;
    private boolean modularCable;
    private List<String> connectors;
}
