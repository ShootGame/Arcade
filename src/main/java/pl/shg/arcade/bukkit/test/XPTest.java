/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.test;

import java.util.UUID;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.development.TestCommand;

/**
 *
 * @author Aleksander
 */
public class XPTest extends TestCommand.Test {
    public XPTest() {
        super("xp", "<calc|drop|give> <uuid> <amount>", true, "operacje systemu XP");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        UUID uuid;
        try {
            uuid = UUID.fromString(args[2]);
        } catch (IllegalArgumentException ex) {
            sender.sendError("Podane UUID nie jest prawidlowe.");
            return;
        }
        
        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException ex) {
            sender.sendError("Podana argument nie moze zostac uznany za liczbe XP");
            return;
        }
        
        if (amount <= 0) {
            sender.sendError("Liczba XP nie moze byc równa zero lub ujemna.");
            return;
        }
    }
    
    @Override
    public int minArguments() {
        return 4;
    }
}
