/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.api.server.Role;

/**
 *
 * @author Aleksander
 */
public class ArcadeOptions extends Options {
    public ArcadeOptions() {
        super();
    }
    
    public String getGlobalPermissionsURL() {
        return this.getValue("global-permissions", "https://raw.githubusercontent.com/ShootGame/Servers/master/_data/arcade-permissions.yml");
    }
    
    public int getReservations() {
        int def = 10;
        try {
            return Integer.valueOf("reservations", def);
        } catch (NumberFormatException ex) {
            return def;
        }
    }
    
    public Role getRole() {
        Role defaults = Role.DEVELOPMENT;
        try {
            return Role.valueOf(this.getValue("role", defaults.name()));
        } catch (IllegalArgumentException ex) {
            return defaults;
        }
    }
    
    public String getServersRepo() {
        return this.getValue("servers", "https://raw.githubusercontent.com/ShootGame/Servers/master");
    }
    
    public boolean isBungeeCordEnabled() {
        boolean def = false;
        try {
            return Boolean.valueOf(this.getValue("bungee", String.valueOf(def)));
        } catch (IllegalArgumentException ex) {
            return def;
        }
    }
}
