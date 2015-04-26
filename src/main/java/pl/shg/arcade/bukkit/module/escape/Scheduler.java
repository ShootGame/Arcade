/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.escape;

/**
 *
 * @author Aleksander
 */
public class Scheduler implements Runnable {
    private static int id;
    
    private final Explosion explosion;
    
    public Scheduler(Explosion explosion) {
        this.explosion = explosion;
    }
    
    @Override
    public void run() {
        this.getExplosion().sendPacket();
    }
    
    public Explosion getExplosion() {
        return this.explosion;
    }
    
    public static int getID() {
        return id;
    }
    
    public static void setID(int id) {
        Scheduler.id = id;
    }
}
