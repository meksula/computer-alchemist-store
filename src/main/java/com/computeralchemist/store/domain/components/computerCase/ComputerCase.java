package com.computeralchemist.store.domain.components.computerCase;

import com.computeralchemist.store.domain.components.ComputerComponent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 09-04-2018
 * */

@Getter
@Setter
public class ComputerCase extends ComputerComponent implements Serializable {

    private long productId;

    private ComputerCaseParameters computerCaseParameters;

}
