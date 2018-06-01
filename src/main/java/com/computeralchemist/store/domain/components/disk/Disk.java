package com.computeralchemist.store.domain.components.disk;

import com.computeralchemist.store.domain.components.ComputerComponent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 07-04-2018
 * */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Disk extends ComputerComponent implements Serializable {

    private long productId;

    private DiskParameters diskParameters;

}
