package com.computeralchemist.store.domain.components.cpu;

import com.computeralchemist.store.domain.components.ComputerComponent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 03-04-2018
 * */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Cpu extends ComputerComponent implements Serializable {

    private long productId;

    private CpuParameters cpuParameters;

}
