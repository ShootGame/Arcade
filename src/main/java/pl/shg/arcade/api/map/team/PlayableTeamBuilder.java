/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map.team;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.permissions.ArcadeTeam;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class PlayableTeamBuilder implements TeamBuilder {
    private ChatChannel chat;
    private TeamColor color;
    private boolean friendlyFire = true;
    private final String id;
    private int minimum = 2;
    private String name;
    private int slots = 15;
    private List<Spawn> spawns;
    
    public PlayableTeamBuilder(String id) {
        Validate.notNull(id, "id can not be null");
        this.id = id;
    }
    
    public TeamColor getTeamColor() {
        return this.color;
    }
    
    public void setTeamColor(TeamColor color) {
        Validate.notNull(color, "color can not be null");
        this.color = color;
    }
    
    @Override
    public boolean isFrendlyFire() {
        return this.friendlyFire;
    }
    
    public void setFrendlyFire(boolean frendlyFire) {
        this.friendlyFire = frendlyFire;
    }
    
    @Override
    public String getID() {
        return this.id;
    }
    
    public int getMinimum() {
        if (this.minimum == 0) {
            return this.slots;
        } else {
            return this.minimum;
        }
    }
    
    public void setMinimum(int minimum) {
        Validate.notNegative(minimum, "minimum can not be negative");
        this.minimum = minimum;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        Validate.notNull(name, "name can not be null");
        this.name = name;
    }
    
    @Override
    public ArcadeTeam getPermissions() {
        return Arcade.getPermissions().getPlayable();
    }
    
    public int getSlots() {
        return this.slots;
    }
    
    public void setSlots(int slots) {
        Validate.notNegative(slots, "slots must be positive");
        Validate.notZero(slots, "slots can not be zero");
        if (slots > Arcade.getServer().getSlots()) {
            throw new ConfigurationException("Ilosc slotów mapy wieksza od serwera");
        }
        this.slots = slots;
    }
    
    @Override
    public List<Spawn> getSpawns() {
        return this.spawns;
    }
    
    public void setSpawns(List<Spawn> spawns) {
        Validate.notNull(spawns, "spawns can not be null");
        this.spawns = spawns;
    }
}
