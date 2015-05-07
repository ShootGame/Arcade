/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.region;

import java.util.List;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class Flag extends FlagEvent {
    public static String DEFAULT_MESSAGE = Color.RED + "Przykro mi, ale nie mozesz tego zrobic!";
    private final String id, message;
    private List<Kit> kits;
    
    public Flag(String id) {
        Validate.notNull(id, "id can not be null");
        this.id = id;
        this.message = null;
    }
    
    public Flag(String id, String message) {
        Validate.notNull(id, "id can not be null");
        this.id = id;
        this.message = message;
    }
    
    public String getID() {
        return this.id;
    }
    
    public List<Kit> getKits() {
        return this.kits;
    }
    
    public String getMessage() {
        if (this.message != null) {
            if (!this.message.toLowerCase().equals("false")) {
                return Color.RED + this.message;
            }
        } else {
            return Flag.DEFAULT_MESSAGE;
        }
        return null;
    }
    
    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }
}
