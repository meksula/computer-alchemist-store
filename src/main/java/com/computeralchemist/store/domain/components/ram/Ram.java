package com.computeralchemist.store.domain.components.ram;

import com.computeralchemist.store.domain.components.ComputerComponent;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class Ram extends ComputerComponent implements Serializable {

    private long productId;

    private RamParameters ramParameters;

}
