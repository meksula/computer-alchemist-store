package com.computeralchemist.store.domain.store.components.supply;

import com.computeralchemist.store.domain.store.components.ComputerComponent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 07-08-2018
 * */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PowerSupply extends ComputerComponent implements Serializable {

    @Id
    private long productId;

    private PowerSupplyParameters powerSupplyParameters;

}
