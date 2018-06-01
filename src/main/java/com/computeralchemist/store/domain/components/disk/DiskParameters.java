package com.computeralchemist.store.domain.components.disk;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DiskParameters implements Serializable {
    private DriveType type;
    private String format;
    private int capacity;
    private double readSpeed;
    private double writeSpeed;
}
