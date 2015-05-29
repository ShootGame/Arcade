/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.classes;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Material;

/**
 *
 * @author Aleksander
 */
public class ClassBuilder {
    private String name, description, fullDescription;
    private Material icon;
    
    public ClassBuilder() {}
    
    public ClassBuilder(ArcadeClass copyOf) {
        Validate.notNull(copyOf, "copyOf can not be null");
        this.name = copyOf.getName();
        this.description = copyOf.getDescription();
    }
    
    public ClassBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public ClassBuilder description(String description) {
        this.description = description;
        return this;
    }
    
    public ClassBuilder fullDescription(String description) {
        this.fullDescription = description;
        return this;
    }
    
    public ClassBuilder icon(Material icon) {
        this.icon = icon;
        return this;
    }
    
    public ArcadeClass toClass() {
        return new ArcadeClass(this.name, this.description, this.fullDescription, this.icon);
    }
}
