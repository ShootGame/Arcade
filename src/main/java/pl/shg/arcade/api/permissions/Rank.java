/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.permissions;

import java.io.File;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
@Deprecated
public final class Rank {
    private List<String> observer;
    private List<String> permissions;
    private String prefix;
    
    public Rank() {
        this.reloadPermissions();
    }
    
    public List<String> getObserverPermissions() {
        return this.observer;
    }
    
    public void setObserverPermissions(List<String> observer) {
        Validate.notNull(observer, "observer can not be null");
        this.observer = observer;
    }
    
    public List<String> getPermissions() {
        return this.permissions;
    }
    
    public void setPermissions(List<String> permissions) {
        Validate.notNull(permissions, "permissions can not ben ull");
        this.permissions = permissions;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void reloadPermissions() {
        this.observer = null;
        this.permissions = null;
        this.prefix = null;
        
        File file = new File(Arcade.getSettingsDirectory() + "permissions.yml");
        if (!file.exists()) {
            file.mkdir();
        }
        // TODO load YAML file via Bukkit API or ...
    }
}
