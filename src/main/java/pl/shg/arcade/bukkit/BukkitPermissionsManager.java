/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.permissions.ObserversTeam;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.shg.arcade.api.permissions.PlayableTeam;
import pl.shg.arcade.api.util.Validate;
import pl.themolka.permissions.Group;
import pl.themolka.permissions.User;

/**
 *
 * @author Aleksander
 */
public class BukkitPermissionsManager implements PermissionsManager {
    private final List<Group> def;
    private FileConfiguration file;
    private final List<Group> groups;
    private final ObserversTeam observers;
    private final PlayableTeam playable;
    
    public BukkitPermissionsManager() {
        this.def = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.observers = new ObserversTeam();
        this.playable = new PlayableTeam();
    }
    
    @Override
    public List<Group> getDefaultGroups() {
        return this.def;
    }
    
    @Override
    public Group getGroup(String name) {
        Validate.notNull(name, "name can not be null");
        for (Group group : this.getGroups()) {
            if (group.getName().toLowerCase().equals(name.toLowerCase())) {
                return group;
            }
        }
        return null;
    }
    
    @Override
    public List<Group> getGroups() {
        return this.groups;
    }
    
    @Override
    public ObserversTeam getObservers() {
        return this.observers;
    }
    
    @Override
    public PlayableTeam getPlayable() {
        return this.playable;
    }
    
    @Override
    public void loadPermissions(String url) {
        try {
            this.file = YamlConfiguration.loadConfiguration(new URL(url).openStream());
        } catch (MalformedURLException ex) {
            Logger.getLogger(PermissionsManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PermissionsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.getFile() != null) {
            this.loadGroups();
            this.loadTeamObservers();
            this.loadTeamPlayable();
        }
    }
    
    @Override
    public void registerDefaultGroup(Group group) {
        Validate.notNull(group, "group can not be null");
        if (this.getGroup(group.getName()) == null) {
            this.registerGroup(group);
        }
        this.def.add(group);
    }
    
    @Override
    public void registerGroup(Group group) {
        Validate.notNull(group, "group can not be null");
        this.groups.add(group);
    }
    
    @Override
    public void reloadAll() {
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            player.getPermissions().reload();
        }
    }
    
    public FileConfiguration getFile() {
        return this.file;
    }
    
    public void setGroupsFor(User user) {
        Validate.notNull(user, "user can not be null");
        // TODO request to out database
        for (Group group : this.getDefaultGroups()) {
            user.addToGroup(group, false);
        }
        // temporary solution
        if (user.getBukkit().getName().equals("TheMolkaPL") || user.getBukkit().getName().equals("FanBlezzura")) {
            user.addToGroup(this.getGroup("developer"), false);
        }
        user.reload();        
    }
    
    private void loadGroups() {
        for (String id : this.getFile().getConfigurationSection("groups").getKeys(false)) {
            String prefix = this.getFile().getString("groups." + id + ".prefix");
            List<String> permissions = this.getFile().getStringList("groups." + id + ".permissions");
            
            Group group = new Group(id);
            if (prefix != null) {
                group.setPrefix(Color.translate(prefix));
            }
            if (permissions != null && !permissions.isEmpty()) {
                group.setPermissions(permissions);
            }
            this.registerGroup(group);
        }
        Log.log(Level.INFO, "Zaladowano " + this.getGroups().size() + " grup uprawnien.");
    }
    
    private void loadTeamObservers() {
        for (String permissions : this.getFile().getStringList("teams.observers")) {
            this.getObservers().addPermission(permissions);
        }
    }
    
    private void loadTeamPlayable() {
        for (String permissions : this.getFile().getStringList("teams.playable")) {
            this.getPlayable().addPermission(permissions);
        }
    }
}
