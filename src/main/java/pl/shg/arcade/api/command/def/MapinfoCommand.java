/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class MapinfoCommand extends Command {
    public MapinfoCommand() {
        super(new String[] {"mapinfo", "map"},
                "Informacje o obecnie granej lub podanej mapie", "map [map...]");
        this.setPermission("arcade.command.mapinfo");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        MapManager maps = this.getPlugin().getMaps();
        if (args.length == 0) {
            show(sender, maps.getCurrentMap());
        } else {
            String targetMap = this.getStringFromArgs(0, args).replace(" ", "_");
            int found = 0;
            Map result = null;
            for (Map map : maps.getMaps()) {
                if (map.getName().toLowerCase().contains(targetMap.toLowerCase())) {
                    found++;
                    result = map;
                }
            }
            
            if (found > 1) {
                sender.sendError("Znaleziono wiecej niz jedna mape. Pokazywanie 1 z " + found + "...");
            }
            
            if (result == null) {
                sender.sendError("Nie znaleziono zadnej mapy o podanych kryteriach.");
            } else {
                show(sender, result);
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    public static void show(Sender sender, Map map) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(map, "map can not be null");
        
        sender.sendMessage(Command.getTitle(map.getDisplayName(), Color.GRAY + map.getVersionString()));
        // Objective
        if (map.getObjective() != null) {
            sender.sendMessage(getLine("Cel gry", map.getObjective()));
        }
        
        // Authors
        String authors = map.getAuthorsString(Color.RED, Color.DARK_PURPLE);
        if (map.getAuthors() == null || map.getAuthors().length > 1) {
            sender.sendMessage(getLine("Autorzy", authors));
        } else {
            sender.sendMessage(getLine("Autor", authors));
        }
    }
    
    private static String getLine(String key, String value) {
        Validate.notNull(key, "key can not be null");
        Validate.notNull(value, "value can not be null");
        return Color.DARK_PURPLE + Color.BOLD + key + ": " + Color.RESET + Color.RED + value;
    }
}
