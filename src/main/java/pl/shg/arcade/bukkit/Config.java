/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Config {
    public static FileConfiguration get(File file) {
        Validate.notNull(file, "file can not be null");
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public static FileConfiguration get(InputStream input) {
        Validate.notNull(input, "input can not be null");
        return YamlConfiguration.loadConfiguration(input);
    }
    
    public static FileConfiguration get(Reader reader) {
        Validate.notNull(reader, "reader can not be null");
        return YamlConfiguration.loadConfiguration(reader);
    }
    
    public static FileConfiguration get(String file) {
        Validate.notNull(file, "file can not be null");
        return YamlConfiguration.loadConfiguration(new File(file));
    }
    
    public static Set<String> getOptions(FileConfiguration config, Module module) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        return getOptionsExact(config, module.getConfigPath());
    }
    
    public static Set<String> getOptions(FileConfiguration config, Module module, String path) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        return getOptionsExact(config, module.getConfigPath() + "." + path);
    }
    
    public static Set<String> getOptionsExact(FileConfiguration config, String path) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(path, "path can not be null");
        return config.getConfigurationSection(path).getKeys(false);
    }
    
    public static boolean getValueBoolean(FileConfiguration config, Module module, String path, boolean def) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        
        String value = getValueString(config, module, path);
        if (value == null) {
            return def;
        }
        
        try {
            return Boolean.valueOf(value);
        } catch (Throwable ex) {
            throw new ConfigurationException(path + " nie moze zostac uznane za boolean");
        }
    }
    
    public static double getValueDouble(FileConfiguration config, Module module, String path, double def) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        
        String value = getValueString(config, module, path);
        if (value == null) {
            return def;
        }
        
        try {
            return Double.valueOf(value);
        } catch (Throwable ex) {
            throw new ConfigurationException(path + " nie moze zostac uznane za double");
        }
    }
    
    public static int getValueInt(FileConfiguration config, Module module, String path, int def) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        
        String value = getValueString(config, module, path);
        if (value == null) {
            return def;
        }
        
        try {
            return Integer.valueOf(value);
        } catch (Throwable ex) {
            throw new ConfigurationException(path + " nie moze zostac uznane za int");
        }
    }
    
    public static List<String> getValueList(FileConfiguration config, Module module, String path) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        return config.getStringList(module.getConfigPath() + "." + path);
    }
    
    public static long getValueLong(FileConfiguration config, Module module, String path, long def) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        
        String value = getValueString(config, module, path);
        if (value == null) {
            return def;
        }
        
        try {
            return Long.valueOf(value);
        } catch (Throwable ex) {
            throw new ConfigurationException(path + " nie moze zostac uznane za long");
        }
    }
    
    public static String getValueMessage(FileConfiguration config, Module module, String message, boolean nullable) {
        return getValueMessage(config, module, null, message, nullable);
    }
    
    public static String getValueMessage(FileConfiguration config, Module module, String path, String def, boolean nullable) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        if (path == null) {
            path = "message";
        }
        
        String value = getValueString(config, module, path);
        if (value == null) {
            value = "default";
        }
        if (value.equalsIgnoreCase("none")) {
            if (nullable) {
                return null;
            } else {
                throw new ConfigurationException(path + " nie moze byc 'none'");
            }
        } else if (value.equalsIgnoreCase("default")) {
            if (def != null) {
                def = Color.translate(def);
            }
            return def;
        } else {
            return value;
        }
    }
    
    public static String getValueString(FileConfiguration config, Module module, String path) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        Validate.notNull(path, "path can not be null");
        
        try {
            return config.getString(module.getConfigPath() + "." + path);
        } catch (Throwable ex) {
            throw new ConfigurationException(path + " nie moze zostac uznane za string");
        }
    }
    
    public static boolean hasOptions(FileConfiguration config, Module module) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        return config.isConfigurationSection(module.getConfigPath());
    }
    
    public static boolean isSet(FileConfiguration config, Module module) {
        Validate.notNull(config, "config can not be null");
        Validate.notNull(module, "module can not be null");
        return config.isConfigurationSection(module.getConfigPath()) || config.getBoolean(module.getConfigPath(), false);
    }
}
