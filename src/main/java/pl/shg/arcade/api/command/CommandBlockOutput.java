/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.command;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class CommandBlockOutput extends ConsoleSender {
    public static final String NAME = "@";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public boolean isConsole() {
        return true; // no but this is used only to check players as command senders
    }
    
    @Override
    public void sendMessage(String message) {
        Validate.notNull(message);
        super.sendMessage("[@] " + message);
    }
}
