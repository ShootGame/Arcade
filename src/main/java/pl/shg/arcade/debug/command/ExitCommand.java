/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.command;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super(new String[] {"exit", "stop", "close"},
                "Wyjdz i zamknij obecna implementacje Arcade", "exit [-r]", new char[] {'r'});
        this.setOption("-r", "Restartuj implementacje");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (this.hasFlag(args, 'r')) {
            // TODO restart
        }
        
        Arcade.getServer().shutdown();
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
