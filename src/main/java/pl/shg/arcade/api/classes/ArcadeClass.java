/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.classes;

import java.util.HashMap;
import java.util.List;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeClass {
    public static final Material DEFAULT_ICON = new Material(276); // Diamond sword
    private final String name, description;
    private String fullDescription;
    private Material icon;
    private HashMap<KitType, List<Kit>> kits = new HashMap<>();
    
    public ArcadeClass(String name, String description) {
        this(name, description, null, null);
    }
    
    public ArcadeClass(String name, String description, String fullDescription, Material icon) {
        Validate.notNull(name, "name can not be null");
        Validate.notNull(description, "description can not be null");
        this.name = name;
        this.description = description;
        this.fullDescription = fullDescription;
        this.icon = icon;
    }
    
    @Override
    public boolean equals(Object obj) {
        Validate.notNull(obj, "obj can not be null");
        if (obj instanceof ArcadeClass) {
            return ((ArcadeClass) obj).getName().equals(this.getName());
        }
        return false;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getFullDescription() {
        if (this.fullDescription != null) {
            return this.fullDescription;
        } else {
            return this.description;
        }
    }
    
    public Material getIcon() {
        if (this.icon != null) {
            return this.icon;
        } else {
            return ArcadeClass.DEFAULT_ICON;
        }
    }
    
    public HashMap<KitType, List<Kit>> getKits() {
        return this.kits;
    }
    
    public boolean hasFullDescription() {
        return this.fullDescription != null;
    }
    
    public boolean hasIcon() {
        return this.icon != null;
    }
    
    public boolean hasKits() {
        return !this.kits.isEmpty();
    }
    
    public void setKits(HashMap<KitType, List<Kit>> kits) {
        if (kits == null) {
            kits = new HashMap<>();
        }
        this.kits = kits;
    }
}
