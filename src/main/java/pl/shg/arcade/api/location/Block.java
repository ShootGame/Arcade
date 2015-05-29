/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.location;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;

/**
 *
 * @author Aleksander
 */
public final class Block {
    private Location location;
    private Material material;
    
    public Block() {}
    
    public Block(Location location) {
        this.setLocation(location);
    }
    
    public Block(Material material) {
        this.setMaterial(material);
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    private void setLocation(Location location) {
        Validate.notNull(location, "location can not be null");
        this.location = location;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial(Material material) {
        Validate.notNull(material, "material can not be null");
        this.material = material;
    }
    
    public static Block fromString(String string) {
        Validate.notNull(string, "string can not be null");
        try {
            return new Block(new Material(string));
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
