/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.classes.ArcadeClass;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class ClassesCommand extends Command {
    public ClassesCommand() {
        super(new String[] {"classes", "klasy", "classlist"},
                "Pokaz liste dostepnych klas na obecnej lub danej mapie", "classes");
        this.setPermission("arcade.command.classes");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (this.getClasses().isEmpty()) {
            sender.sendError("Brak dostepnych klas na obecnej mapie.");
        } else {
            sender.sendMessage(Command.getTitle("Dostepne klasy", Color.GRAY + "(" + this.getClasses().size() + ")"));
            this.printClasses((Player) sender);
            if (!Arcade.getMaps().getCurrentMap().isSwitchingClassAllowed()) {
                sender.sendMessage(Color.RED + "Uwaga: " + Color.YELLOW + ClassCommand.CLASSES_DISABLED);
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private List<ArcadeClass> getClasses() {
        return Arcade.getMaps().getCurrentMap().getClasses();
    }
    
    private void printClasses(Player player) {
        List<ArcadeClass> classes = this.getClasses();
        for (int i = 0; i < this.getClasses().size(); i++) {
            ArcadeClass clazz = classes.get(i);
            player.sendMessage(Color.RED + (i + 1) + ". " + Color.AQUA + clazz.getName()
                + Color.RED + " - " + Color.YELLOW + clazz.getDescription());
        }
    }
}
