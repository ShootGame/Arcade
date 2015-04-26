/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

/**
 *
 * @author Aleksander
 */
public class Direction extends Location {
    private float yaw;
    private float pitch;
    
    public Direction(double x, int y, double z, float yaw, float pitch) {
        super(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public int getStaticY() {
        return (int) this.getY();
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
