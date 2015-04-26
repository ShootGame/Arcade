/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.status;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class CurrentServerStatus extends ArcadeServerStatus {
    public CurrentServerStatus() {
        super(Arcade.getServers().getCurrentServer());
    }
    
    @Override
    public boolean enabled() {
        return true;
    }
    
    @Override
    public String canConnect(Player player) {
        return "Obecnie znajdujesz sie juz na " + this.getServer().getName() + "!";
    }
    
    @Override
    public int online() {
        return Arcade.getServer().getOnlinePlayers().size();
    }
    
    @Override
    public String rawMOTD() {
        return null;
    }
    
    @Override
    public int slots() {
        return Arcade.getServer().getSlots();
    }
}
