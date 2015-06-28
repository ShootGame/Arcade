/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.cy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.filter.ApplyFilter;
import pl.shg.arcade.api.filter.Filter;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.location.Block;
import pl.shg.arcade.api.location.BlockLocation;
import pl.shg.arcade.api.location.Direction;
import pl.shg.arcade.api.region.BreakFlag;
import pl.shg.arcade.api.region.Flag;
import pl.shg.arcade.api.region.InteractFlag;
import pl.shg.arcade.api.region.KillFlag;
import pl.shg.arcade.api.region.MoveFlag;
import pl.shg.arcade.api.region.PlaceFlag;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.api.region.TeleportFlag;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class CyRegionsLoader {
    private final FileConfiguration f;
    private final String section = "regions";
    private List<Region> regions;
    
    public CyRegionsLoader(FileConfiguration f) {
        this.f = f;
        this.loadRegions();
    }
    
    public BlockLocation getCoord(String path) {
        int x = this.f.getInt(path + ".x");
        int y = this.f.getInt(path + ".y");
        int z = this.f.getInt(path + ".z");
        return new BlockLocation(x, y, z);
    }
    
    public Filter getFilter(String filter, String path) {
        List<Material> accept = new ArrayList<>();
        List<Material> deny = new ArrayList<>();
        
        if (this.f.isSet(path + ".accept")) {
            for (String listElement : this.f.getStringList(path + ".accept")) {
                Material material = null;
                try {
                    material = new Material(listElement);
                } catch (NumberFormatException ex) {
                    Log.noteAdmins("ID bloku " + listElement + " w filtrze " + filter +
                            " nie zostalo rozpoznane (accept).", Log.NoteLevel.WARNING);
                }
                
                if (material != null) {
                    accept.add(material);
                }
            }
        }
        
        if (this.f.isSet(path + ".deny")) {
            for (String listElement : this.f.getStringList(path + ".deny")) {
                Material material = null;
                try {
                    material = new Material(listElement);
                } catch (NumberFormatException ex) {
                    Log.noteAdmins("ID bloku " + listElement + " w filtrze " + filter +
                            " nie zostalo rozpoznane (deny).", Log.NoteLevel.WARNING);
                }
                
                if (material != null) {
                    accept.add(material);
                }
            }
        }
        
        Filter filterObj = null;
        switch (filter.toLowerCase()) {
            case "apply":
                filterObj = new ApplyFilter(accept, deny);
                break;
        }
        return filterObj;
    }
    
    public Flag getFlag(String flag, Team owner, String path) {
        List<Block> allowed = new ArrayList<>();
        for (String b : this.f.getStringList(path + ".allowed")) {
            Block block = Block.fromString(b);
            if (block != null) {
                allowed.add(block);
            }
        }
        
        int x = this.f.getInt(path + ".x");
        int y = this.f.getInt(path + ".y");
        int z = this.f.getInt(path + ".z");
        float yaw = this.f.getInt(path + ".yaw");
        float pitch = this.f.getInt(path + ".pitch");
        Direction direction = new Direction(x, y, z, yaw, pitch);
        
        String message = this.f.getString(path + ".message", null);
        
        Flag flagObj = null;
        switch (flag.toLowerCase()) {
            case "break":
                flagObj = new BreakFlag(allowed, owner, message);
                break;
            case "interact":
                flagObj = new InteractFlag(allowed, owner, message);
                break;
            case "kill":
                flagObj = new KillFlag(owner, message);
                break;
            case "move":
                flagObj = new MoveFlag(owner, message);
                break;
            case "place":
                flagObj = new PlaceFlag(allowed, owner, message);
                break;
            case "teleport":
                flagObj = new TeleportFlag(direction, owner, message);
                break;
        }
        
        if (flagObj != null) {
            HashMap<KitType, List<Kit>> kits = CyKitsLoader.getDefinedKits(this.f, path);
            if (kits != null && kits.containsKey(KitType.ACTION)) {
                flagObj.setKits(kits.get(KitType.ACTION));
            }
        }
        return flagObj;
    }
    
    public List<Region> getRegions() {
        return this.regions;
    }
    
    private void loadRegion(String region) {
        String path = this.section + "." + region;
        BlockLocation max = this.getCoord(path + ".max");
        BlockLocation min = this.getCoord(path + ".min");
        
        Region regionObj = new Region(min, max, region);
        String team = this.f.getString(path + ".owner");
        Team teamObj = null;
        if (team != null) {
            teamObj = Arcade.getTeams().getTeam(team);
            if (teamObj == null) {
                Log.noteAdmins("Druzyna '" + team + "'do regionu '" + region +
                        "' nie zostala odnaleziona.", Log.NoteLevel.WARNING);
            }
        }
        if (teamObj != null) {
            regionObj.setOwner(teamObj);
        }
        
        String filterPath = path + ".filters";
        if (this.f.getConfigurationSection(filterPath) != null) {
            for (String filter : this.f.getConfigurationSection(filterPath).getKeys(false)) {
                Filter filterObj = this.getFilter(filter, filterPath + "." + filter);
                if (filterObj != null) {
                    regionObj.addFilter(filterObj);
                } else {
                    Log.noteAdmins("Filtr '" + filter + "' do regionu '" + regionObj.getPath() +
                            "' nie zostala rozpoznana", Log.NoteLevel.WARNING);
                }
            }
        }
        
        String flagPath = path + ".flags";
        if (this.f.getConfigurationSection(flagPath) != null) {
            for (String flag : this.f.getConfigurationSection(flagPath).getKeys(false)) {
                Flag flagObj = this.getFlag(flag, teamObj, flagPath + "." + flag);
                if (flagObj != null) {
                    regionObj.addFlag(flagObj);
                } else {
                    Log.noteAdmins("Flaga '" + flag + "' do regionu '" + regionObj.getPath() +
                            "' nie zostala rozpoznana", Log.NoteLevel.WARNING);
                }
            }
        }
        
        this.regions.add(regionObj);
    }
    
    private void loadRegions() {
        this.regions = new ArrayList<>();
        for (String region : this.f.getConfigurationSection(this.section).getKeys(false)) {
            this.loadRegion(region);
        }
    }
}
