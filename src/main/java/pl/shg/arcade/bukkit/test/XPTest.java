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
import pl.shg.commons.documents.DocumentArray;
import pl.shg.commons.users.DBUser;
import pl.shg.commons.users.XPDocument;
import pl.shg.commons.users.XPHelper;

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
        
        if (args[1].equalsIgnoreCase("calc")) {
            this.calc(sender, uuid);
        } else if (args[1].equalsIgnoreCase("drop")) {
            this.drop(uuid, amount);
        } else if (args[1].equalsIgnoreCase("give")) {
            this.give(uuid, amount);
        } else {
            sender.sendError("Nie zrozumiano polecenia \"" + args[1] + "\".");
        }
    }
    
    @Override
    public int minArguments() {
        return 4;
    }
    
    private void calc(final Sender sender, UUID uuid) {
        this.getDatabase().calculateXP(new DBUser(uuid), new DocumentArray() {
            @Override
            public void run(Object[] array) {
                int xp = (int) array[0];
                DBUser user = (DBUser) array[1];
                
                sender.sendSuccess(user.getUUID() + " posiada " + xp + " XP.");
            }
        });
    }
    
    private void drop(UUID uuid, int amount) {
        this.getDatabase().dropIfHasXP(new DBUser(uuid), amount, "Testy XP");
    }
    
    private XPHelper getDatabase() {
        return (XPHelper) XPDocument.getDocument().getHelper();
    }
    
    private void give(UUID uuid, int amount) {
        this.getDatabase().giveXP(new DBUser(uuid), amount, "Testy XP");
    }
}
