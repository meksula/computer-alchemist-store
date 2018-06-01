package com.computeralchemist.store.domain.components;

import com.computeralchemist.store.domain.components.computerCase.ComputerCase;
import com.computeralchemist.store.domain.components.cpu.Cpu;
import com.computeralchemist.store.domain.components.disk.Disk;
import com.computeralchemist.store.domain.components.gpu.GraphicsCard;
import com.computeralchemist.store.domain.components.motherboard.Motherboard;
import com.computeralchemist.store.domain.components.ram.Ram;
import com.computeralchemist.store.domain.components.supply.PowerSupply;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Author
 * Karol Meksuła
 * 06-04-2018
 * */

public enum JsonParsers {

    motherboard {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, Motherboard.class);
        }
    },

    cpu {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, Cpu.class);
        }
    },

    ram {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, Ram.class);
        }
    },

    disk {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, Disk.class);
        }
    },

    supply {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, PowerSupply.class);
        }
    },

    gpu {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, GraphicsCard.class);
        }
    },

    computercase {
        @Override
        public ComputerComponent parseStringToComponent(String json) throws IOException {
            return objectMapper.readValue(json, ComputerCase.class);
        }
    };

    public abstract ComputerComponent parseStringToComponent(String json) throws IOException;

    protected ObjectMapper objectMapper = new ObjectMapper();
}
