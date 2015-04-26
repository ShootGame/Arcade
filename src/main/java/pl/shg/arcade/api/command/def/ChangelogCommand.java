/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class ChangelogCommand extends Command {
    public ChangelogCommand() {
        super(new String[] {"changelog", "changes", "zmiany", "history", "historia"},
                "Pokaz historie zmian obecnej lub danej mapy", "changelog [map...]");
        this.setPermission("arcade.command.changelog");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
