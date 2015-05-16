/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.util.HashMap;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.map.GameableBlock;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class BlocksDestroyable implements Destroyable {
    private List<Monument> monuments;
    private final String name;
    private final HashMap<Setting, Object> settings;
    private final Team team;
    
    public BlocksDestroyable(String name, Team team) {
        this.name = name;
        this.settings = new HashMap<>();
        this.team = team;
    }
    
    @Override
    public void appendSetting(Setting setting, Object value) {
        this.settings.put(setting, value);
    }
    
    @Override
    public boolean canDestroy(Player player, BlockLocation block) {
        for (Monument monument : this.getMonuments()) {
            if (monument.getBlock().getLocation().equals(block)) {
                return monument.canBreak(player);
            }
        }
        return false;
    }
    
    @Override
    public void destroy(Player player) {
        Event.callEvent(new DestroyableDestroyedEvent(this, player));
        
        Arcade.getServer().checkEndMatch();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getPercent() {
        int destroyed = 0;
        for (Monument monument : this.getMonuments()) {
            if (monument.isDestroyed()) {
                destroyed++;
            }
        }
        return (100 / this.getMonuments().size()) * destroyed;
    }
    
    @Override
    public Object getSettingValue(Setting setting) {
        return this.settings.get(setting);
    }
    
    @Override
    public DestroyStatus getStatus() {
        return DestroyStatus.UNTOUCHED;
    }
    
    public List<Monument> getMonuments() {
        return this.monuments;
    }
    
    public Team getTeam() {
        return this.team;
    }
    
    public void setMonuments(List<Monument> monuments) {
        this.monuments = monuments;
        
        if (monuments != null) {
            for (Monument monument : monuments) {
                GameableBlock.register(monument);
            }
        }
    }
}
