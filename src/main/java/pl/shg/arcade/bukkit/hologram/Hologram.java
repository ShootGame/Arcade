/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.hologram;

import org.bukkit.entity.ArmorStand;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class Hologram {
    private String hologram;
    private final ArmorStand stand;
    
    public Hologram(ArmorStand stand) {
        this(stand, null);
    }
    
    public Hologram(ArmorStand stand, String hologram) {
        this.stand = stand;
        this.hologram = hologram;
        
        this.stand.setCustomNameVisible(true);
        this.stand.setGravity(false);
        this.stand.setVisible(false);
    }
    
    public void append(String hologram) {
        this.hologram = hologram;
    }
    
    public void appendNew(String hologram) {
        this.append(hologram + Color.RESET);
    }
    
    public String getHologram() {
        return this.hologram;
    }
    
    public ArmorStand getStand() {
        return this.stand;
    }
    
    public void setHologram(String hologram) {
        this.hologram = hologram;
    }
    
    public final void update() {
        if (this.getHologram() != null) {
            this.stand.setCustomName(this.getHologram());
        } else {
            this.stand.remove(); // remove this entity
        }
    }
}
