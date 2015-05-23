/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import java.util.List;
import pl.shg.arcade.api.permissions.ObserversTeam;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.shg.arcade.api.permissions.PlayableTeam;
import pl.themolka.permissions.Group;

/**
 *
 * @author Aleksander
 */
public class DebugPermissionsManager implements PermissionsManager {
    @Override
    public List<Group> getDefaultGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Group getGroup(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Group> getGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObserversTeam getObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public PlayableTeam getPlayable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void loadPermissions(String url) {
        
    }
    
    @Override
    public void registerDefaultGroup(Group group) {
        
    }
    
    @Override
    public void registerGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void reloadAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
