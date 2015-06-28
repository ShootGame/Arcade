/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command;

import java.util.List;

/**
 *
 * @author Aleksander
 */
public interface CommandManager {
    CommandBlockOutput getCommandBlock();
    
    List<Command> getCommands();
    
    ConsoleSender getConsoleSender();
    
    boolean perform(String command, Sender sender, String[] args);
    
    boolean performAlias(String alias, Sender sender, String[] args);
    
    void registerCommand(Command command);
}
