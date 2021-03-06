/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.human.VisibilityFilter;
import pl.shg.arcade.api.kit.KitType;

/**
 *
 * @author Aleksander
 */
public interface PlayerManagement {
    void addPotion(Player player, String id, int level, int time);
    
    VisibilityFilter getVisibility();
    
    boolean isGhost(Player player);
    
    void playSound(Player player, Sound sound);
    
    void playSound(Player player, Sound sound, float volume, float pitch);
    
    void refreshHiderForAll();
    
    void setAsObserver(Player player, boolean fullKit, boolean hider, boolean perms);
    
    void setAsPlayer(Player player, KitType kit, boolean hider, boolean sendTitle, boolean perms);
    
    void setGhost(Player player, boolean ghost);
    
    void setVisibility(VisibilityFilter visibility);
}
