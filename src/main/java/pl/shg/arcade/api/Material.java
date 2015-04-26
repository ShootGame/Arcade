/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import java.util.Arrays;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class Material {
    private int id, subID;
    
    public Material(int id) {
        this.setID(id);
        this.setSubID(0);
    }
    
    public Material(int id, int subId) {
        this.setID(id);
        this.setSubID(subId);
    }
    
    public Material(String string) throws NumberFormatException {
        Validate.notNull(string, "string can not be null");
        String[] list = string.split("-", 2);
        int newID = Integer.valueOf(list[0]);
        int newSubID = 0;
        if (list.length > 1) {
            newSubID = Integer.valueOf(list[1]);
        }
        
        this.setID(newID);
        this.setSubID(newSubID);
    }
    
    public String asString() {
        String result = "";
        if (this.hasSubID()) {
            result = "-" + this.getSubID();
        }
        return this.getID() + result;
    }
    
    public int getID() {
        return this.id;
    }
    
    private void setID(int id) {
        Validate.notNegative(id, "id can not be negative");
        Validate.notZero(id, "id can not be zero");
        this.id = id;
    }
    
    public int getSubID() {
        return this.subID;
    }
    
    public boolean hasSubID() {
        return this.getSubID() != 0;
    }
    
    private void setSubID(int subId) {
        Validate.notNegative(subId, "subId can not be negative");
        this.subID = subId;
    }
}
