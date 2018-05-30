package com.computeralchemist.store.domain.store.components.gpu;

import com.computeralchemist.store.domain.store.components.ComputerComponent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 08-04-2018
 * */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GraphicsCard extends ComputerComponent implements Serializable {

    private long productId;

    private GraphicsCardParameters graphicsCardParameters;

}
