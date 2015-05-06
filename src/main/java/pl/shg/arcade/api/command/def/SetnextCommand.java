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
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapManager;

/**
 *
 * @author Aleksander
 */
public class SetnextCommand extends Command {
    private static boolean sameMap = false;
    
    public SetnextCommand() {
        super(new String[] {"setnext", "sn"},
                "Ustaw nastepna mape w kolejce", "setnext <-r|map...>",
                new char[] {'r'});
        this.setOption("-r", "restartuj serwer");
        this.setPermission("arcade.command.setnext");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (this.hasFlag(args, 'r')) {
            if (!this.canNot(sender, "arcade.command.setnext.restart")) {
                Arcade.getMaps().setNextMap(null);
                sender.sendSuccess("Po zakonczeniu meczu serwer zostanie zrestartowany.");
            }
        } else {
            MapManager maps = Arcade.getMaps();
            String mapString = this.getStringFromArgs(0, args).replace(" ", "_");
            
            Map map = maps.getMapExact(mapString);
            if (map == null) {
                map = maps.getMap(mapString);
            }
            if (map == null) {
                sender.sendError("Podana przez Ciebie mapa nie istnieje.");
                return;
            }
            
            if (maps.getCurrentMap().equals(maps.getNextMap())) {
                if (sameMap) {
                    sender.sendError("Nastepna mapa jest taka sama co obecna!");
                    sender.sendError("Ustawienie obecnej mapy jako nastepnej nie jest zalecane.");
                } else {
                    sender.sendError("Nie mozesz ustawic nastepnej mapy takiej samej jak obecna.");
                    return;
                }
            }
            
            maps.setNextMap(map);
            sender.sendSuccess("Mapa " + map.getDisplayName() + " zostala ustawiona jako nastepna w kolejce.");
        }
    }
    
    @Override
    public int minArguments() {
        return 1;
    }
}
