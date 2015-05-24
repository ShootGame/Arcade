/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.KitType;

/**
 *
 * @author Aleksander
 */
public class DebugPlayerManagement implements PlayerManagement {
    @Override
    public void addPotion(Player player, String id, int level, int time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isGhost(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void playSound(Player player, Sound sound) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void playSound(Player player, Sound sound, float volume, float pitch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void refreshHiderForAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setAsObserver(Player player, boolean fullKit, boolean hider, boolean perms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setAsPlayer(Player player, KitType kit, boolean hider, boolean sendTitle, boolean perms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void setGhost(Player player, boolean ghost) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
