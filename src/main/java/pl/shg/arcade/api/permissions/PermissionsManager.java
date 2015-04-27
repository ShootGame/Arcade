/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.permissions;

import java.util.List;
import pl.themolka.permissions.Group;

/**
 *
 * @author Aleksander
 */
public interface PermissionsManager {
    public static String SERVER_INTERNAL = "si-";
    
    List<Group> getDefaultGroups();
    
    Group getGroup(String name);
    
    List<Group> getGroups();
    
    ObserversTeam getObservers();
    
    PlayableTeam getPlayable();
    
    @Deprecated
    void loadPermissions(String url);
    
    void registerDefaultGroup(Group group);
    
    void registerGroup(Group group);
    
    void reloadAll();
}
