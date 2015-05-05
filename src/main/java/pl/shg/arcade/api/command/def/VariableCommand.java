/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class VariableCommand extends Command {
    private static Performer performer;
    
    public VariableCommand() {
        super(new String[] {"variable", "var"},
                "Pokaz lub zmien wartosc zmiennej w danym module",
                "variable <[package/]module> [variable] [value]");
        this.setPermission("arcade.command.variable");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendError("Podaj nazwe klasy modulu!");
            sender.sendError(this.getUsage());
        } else if (getPerformer() != null) {
            getPerformer().perform(sender, args[0].replace("/", "."), args);
        } else {
            sender.sendError("Brak implementacji dla tej komendy.");
        }
        
    }
    
    @Override
    public int minArguments() {
        return 1;
    }
    
    public static Performer getPerformer() {
        return performer;
    }
    
    public static void setPerformer(Performer performer) {
        VariableCommand.performer = performer;
    }
    
    public static interface Performer {
        void perform(Sender sender, String target, String[] args);
    }
}
