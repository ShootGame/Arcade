/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class TutorialCommand extends Command {
    public TutorialCommand() {
        super(new String[] {"tutorial", "tut", "poradnik", "guide", "przewodnik"},
                "Pokaz poradnik gry na obecnej mapie", "tutorial [page]");
        this.setPermission("arcade.command.tutorial");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        Tutorial tutorial = Arcade.getMaps().getCurrentMap().getTutorial();
        int page = 1;
        if (args.length > 0) {
            page = this.parseInteger(args[0], 0);
        }
        handleTutorial(sender, tutorial, page);
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    public static void handleTutorial(Sender sender, Tutorial tutorial, int page) {
        if (tutorial.isEmpty()) {
            sender.sendError("Przepraszamy, na tej mapie poradnik nie jest dostepny.");
            sender.sendError("Staramy sie go dodac jak najszybciej!");
        } else if (tutorial.getPages().size() < page || page < 0) {
            sender.sendError("Nie znaleziono strony " + page + ".");
        } else {
            Tutorial.Page tutPage = tutorial.getPages().get(page - 1);
            sender.sendMessage(Command.getTitle("Poradnik", Color.GRAY + "(" + page + "/" + tutorial.getPages().size() + ")"));
            sender.sendMessage(Color.DARK_PURPLE + Color.ITALIC + "Tryb gry: " +
                    Color.RESET + Color.GREEN + Color.BOLD + tutPage.getGameMode());
            sender.sendMessage(Color.GRAY + tutPage.getText());
            
            if (tutorial.getPages().size() > page) {
                sender.sendMessage(Color.DARK_PURPLE + Color.ITALIC + "Aby przeczytac nastepna strone poradnika uzyj " +
                        Color.GREEN + "/tutorial " + (page + 1) + Color.RESET + Color.DARK_PURPLE + Color.ITALIC + ".");
            }
        }
    }
}
