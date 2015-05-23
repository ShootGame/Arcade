/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.command;

/**
 *
 * @author Aleksander
 */
public interface CommandExecutor {
    void execute(Sender sender, String[] args) throws CommandException;
    
    int minArguments();
}
