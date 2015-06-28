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
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.classes.ArcadeClass;
import pl.shg.arcade.api.classes.ClassBuilder;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.kit.KitType;

/**
 *
 * @author Aleksander
 */
public class CyClassesLoader {
    private final FileConfiguration f;
    private final String section = "classes";
    private boolean switching = true;
    private List<ArcadeClass> classes;
    
    public CyClassesLoader(FileConfiguration f) {
        this.f = f;
        this.loadClasses();
    }
    
    public List<ArcadeClass> getClasses() {
        return this.classes;
    }
    
    public Material getIcon(String path) {
        String icon = this.f.getString(path);
        if (icon != null) {
            try {
                return new Material(icon);
            } catch (NumberFormatException ex) {}
        }
        return null;
    }
    
    public boolean isSwitchingAllowed() {
        return this.switching;
    }
    
    private HashMap<KitType, List<Kit>> getKits(String clazz) {
        HashMap<KitType, List<Kit>> kits = new HashMap<>();
        String path = this.section + "." + clazz + ".kits";
        if (this.f.get(path) == null) {
            return null;
        }
        
        for (String kit : this.f.getConfigurationSection(path).getKeys(false)) {
            List<Kit> list = new ArrayList<>();
            
            KitType type;
            try {
                type = KitType.valueOf(kit.toUpperCase());
            } catch (IllegalArgumentException ex) {
                type = KitType.BEGIN;
            }
            
            List<String> listString = new ArrayList<>();
            String string = this.f.getString(path + "." + kit);
            if (string != null) {
                listString.add(string);
            } else {
                listString.addAll(this.f.getStringList(path + "." + kit));
            }
            
            for (String kitString : listString) {
                Kit kitObj = Arcade.getTeams().getKit(kitString);
                list.add(kitObj);
            }
            
            kits.put(type, list);
        }
        return kits;
    }
    
    private void loadClass(String clazz) {
        String path = this.section + "." + clazz;
        
        ClassBuilder builder = new ClassBuilder();
        builder.name(clazz);
        builder.description(this.f.getString(path + ".description"));
        builder.fullDescription(this.f.getString(path + ".full"));
        builder.icon(this.getIcon(path + ".icon"));
        
        ArcadeClass classObj = builder.build();
        classObj.setKits(this.getKits(clazz));
        this.classes.add(classObj);
    }
    
    private void loadClasses() {
        this.classes = new ArrayList<>();
        for (String clazz : this.f.getConfigurationSection(this.section).getKeys(false)) {
            if (clazz.toLowerCase().equals("options")) {
                this.loadOptions(clazz);
            } else {
                this.loadClass(clazz);
            }
        }
    }
    
    private void loadOptions(String options) {
        String path = this.section + "." + options;
        for (String option : this.f.getConfigurationSection(path).getKeys(false)) {
            switch (option.toLowerCase()) {
                case "switching":
                    this.switching = this.f.getBoolean(path + "." + option, true);
                    break;
            }
        }
    }
}
