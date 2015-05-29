/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public final class Material {
    public static final char ID_SEPARATOR = '-';
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
        String[] list = string.split(String.valueOf(Material.ID_SEPARATOR), 2);
        int newID = Integer.valueOf(list[0]);
        int newSubID = 0;
        if (list.length > 1) {
            newSubID = Integer.valueOf(list[1]);
        }
        
        this.setID(newID);
        this.setSubID(newSubID);
    }
    
    @Override
    public boolean equals(Object obj) {
        Validate.notNull(obj, "obj can not be null");
        if (obj instanceof Material) {
            Material material = (Material) obj;
            return material.getID() == this.getID() && material.getSubID() == this.getSubID();
        }
        return false;
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
        Validate.isTrue(id > 0);
        this.id = id;
    }
    
    public int getSubID() {
        return this.subID;
    }
    
    public boolean hasSubID() {
        return this.getSubID() != 0;
    }
    
    private void setSubID(int subId) {
        Validate.isTrue(subId >= 0);
        this.subID = subId;
    }
}
