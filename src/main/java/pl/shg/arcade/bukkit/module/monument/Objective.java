/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.monument;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.location.GameableBlock;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Objective {
    private final MonumentModule module;
    private final List<Monument> monuments = new ArrayList<>();
    private final String name;
    private final Team owner;
    
    public Objective(MonumentModule module, String name, Team owner) {
        Validate.notNull(module, "module can not be null");
        Validate.notNull(name, "name can not be null");
        Validate.notNull(owner, "owner can not be null");
        this.module = module;
        this.name = name;
        this.owner = owner;
    }
    
    public void addMonument(Monument monument) {
        Validate.notNull(monument, "monument can be null");
        this.monuments.add(monument);
        GameableBlock.register(monument);
    }
    
    public String getColor() {
        switch (this.getStatus()) {
            case DESTROYED: return Color.DARK_RED;
            case TOUCHED: return Color.GREEN; //return Color.YELLOW;
            case UNTOUCHED: return Color.GREEN;
            default: return null;
        }
    }
    
    public int getDestroyed() {
        int destroyed = 0;
        for (Monument monument : this.getMonuments()) {
            if (monument.isDestroyed()) {
                destroyed++;
            }
        }
        return destroyed;
    }
    
    public String getDisplayName() {
        return this.getName().replace('_', ' ');
    }
    
    public MonumentModule getModule() {
        return this.module;
    }
    
    public List<Monument> getMonuments() {
        return this.monuments;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    public String getScoreboardID() {
        return (this.getOwner().getID() + "-" + this.getName()).toLowerCase();
    }
    
    public Status getStatus() {
        if (this.isDestroyed()) {
            return Status.DESTROYED;
        } else if (this.getDestroyed() == 0) {
            return Status.UNTOUCHED;
        } else {
            return Status.TOUCHED;
        }
    }
    
    public boolean isDestroyed() {
        return this.getDestroyed() == this.getMonuments().size();
    }
    
    public boolean removeMonument(Monument monument) {
        Validate.notNull(monument, "monument can not be null");
        return this.monuments.remove(monument);
    }
    
    public static enum Status {
        UNTOUCHED, TOUCHED, DESTROYED;
    }
}
