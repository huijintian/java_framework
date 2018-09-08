package com.soa.spi;

import java.util.ServiceLoader;

/**
 * Created by mengtian on 2018/9/8
 */
public class SpiMain {

    public static void main(String[] args) {
        ServiceLoader<Command> commands = ServiceLoader.load(Command.class);
        for (Command command : commands) {
            command.execute();
        }
    }
}
