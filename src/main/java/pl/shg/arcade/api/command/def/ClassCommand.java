/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander JagieĹ‚Ĺ‚o <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import java.util.List;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.classes.ArcadeClass;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.PlayerSetClassEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class ClassCommand extends Command {
    public static final String CLASSES_DISABLED = "Zmiana klasy w czasie gry na tej mapie za pomoca komendy /class jest wylaczona.";
    
    public ClassCommand() {
        super(new String[] {"class", "klasa"},
                "Wybierz klase lub pokaz obecna w której jestes", "class [class]");
        this.setPermission("arcade.command.class");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (this.getClasses().isEmpty()) {
            sender.sendError("Brak dostepnych klas na obecnej mapie.");
        } else if (sender.isConsole()) {
            this.throwMessage(sender, CommandMessage.PLAYER_NEEDED);
        } else if (args.length == 0) {
            this.showCurrent((Player) sender);
        } else if (this.switchingDisabled(sender)) {
            sender.sendError(ClassCommand.CLASSES_DISABLED);
        } else if (Arcade.getMatches().getStatus() == MatchStatus.ENDING) {
            sender.sendError("Mecz sie zakonczyl! " + Color.GOLD + "Poczekaj na serwer az zaladuje nowa mape.");
        } else {
            this.setClass((Player) sender, this.getStringFromArgs(0, args));
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private List<ArcadeClass> getClasses() {
        return Arcade.getMaps().getCurrentMap().getClasses();
    }
    
    private void setClass(Player player, String classString) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(classString, "classString can not be null");
        Map map = Arcade.getMaps().getCurrentMap();
        ArcadeClass clazz = map.getClassExact(classString);
        
        if (clazz == null) {
            clazz = map.findClass(classString);
        }
        if (clazz != null) {
            if (player.getArcadeClass().equals(clazz)) {
                player.sendError("Juz posiadasz klase " + clazz.getName() + ".");
            } else {
                PlayerSetClassEvent event = new PlayerSetClassEvent(clazz, player);
                Event.callEvent(event);
                
                if (!event.isCancel()) {
                    player.setArcadeClass(clazz);
                    player.sendMessage(Color.GRAY + "Zmieniono klase na " + Color.GREEN +
                            Color.BOLD + clazz.getName() + Color.RESET + Color.GRAY + ".");
                    if (!player.isObserver() && Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
                        player.setHealth(0.0);
                    }
                }
            }
        } else {
            player.sendError("Nie znaleziono zadnej klasy z zapytania " + classString + ".");
        }
    }
    
    private void showCurrent(Player player) {
        Validate.notNull(player, "player can not be null");
        ArcadeClass clazz = player.getArcadeClass();
        
        if (clazz == null) {
            clazz = this.getClasses().get(0);
        }
        player.sendMessage(Color.GRAY + "Twoja obecna klasa to " + Color.RED + clazz.getName() + Color.GRAY + ".");
    }
    
    private boolean switchingDisabled(Sender sender) {
        return Arcade.getMatches().getStatus() == MatchStatus.PLAYING && !((Player) sender).isObserver()
                && !Arcade.getMaps().getCurrentMap().isSwitchingClassAllowed();
    }
}
