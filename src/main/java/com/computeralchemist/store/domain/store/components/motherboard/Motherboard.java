package com.computeralchemist.store.domain.store.components.motherboard;

import com.computeralchemist.store.domain.store.components.ComputerComponent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 30-03-2018
 * */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Motherboard extends ComputerComponent implements Serializable {

    private long productId;

    private MotherboardParameters motherboardParameters;

}
