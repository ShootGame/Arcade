/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ModuleException;
import pl.shg.arcade.api.module.ModuleManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeModuleManager implements ModuleManager {
    private final HashMap<String, Module> active;
    private final HashMap<String, Class<? extends Module>> modules;
    
    public ArcadeModuleManager() {
        this.active = new HashMap<>();
        this.modules = new HashMap<>();
    }
    
    @Override
    public void active(Module module) {
        Validate.notNull(module, "module can not be null");
        if (module.getID().length() > 25) {
            throw new UnsupportedOperationException("Module " + module.getID() + " is not supported (danger ID).");
        } else {
            List<Class<? extends Module>> dependencies = module.getDependencies(Module.DependencyType.STRONG);
            // Dependency
            for (Class<? extends Module> dependency : dependencies) {
                Log.log(Level.INFO, "Dependency: " + dependency.getName());
                this.active(this.asObject(dependency));
            }
        }
        
        this.active.put(module.getID(), module);
        Log.log(Level.INFO, "Aktywowano modul " + module.getID() + ".");
    }
    
    @Override
    public Module asObject(Class<? extends Module> module) {
        try {
            return module.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ArcadeModuleManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Collection<Module> getActiveModules() {
        return this.active.values();
    }
    
    @Override
    public void inactive(Module module) {
        Validate.notNull(module, "module can not be null");
        module.unload();
        this.active.remove(module.getID());
        Log.log(Level.INFO, "Zdezaktywowano modul " + module.getID() + ".");
    }
    
    @Override
    public void inactiveAll() {
        if (!this.getActiveModules().isEmpty()) {
            int amount = this.getActiveModules().size();
            for (Module module : this.getActiveModules()) {
                module.unload();
            }
            this.active.clear();
            Log.log(Level.INFO, "Zdezaktywowano " + amount + " modulow.");
        }
    }
    
    @Override
    public Class<? extends Module> getModuleExact(String id) {
        Validate.notNull(id, "id can not be null");
        return this.modules.get(id.toLowerCase());
    }
    
    @Override
    public Collection<Class<? extends Module>> getModules() {
        return this.modules.values();
    }
    
    @Override
    public void register(Class<? extends Module> module) throws ModuleException {
        Validate.notNull(module, "module can not be null");
        if (!this.modules.containsValue(module)) {
            Module object = this.asObject(module);
            if (module != null) {
                this.modules.put(object.getID(), module);
                Log.log(Level.INFO, "Modul " + module.getSimpleName() + " zostal zarejestrowany");
            }
        }
    }
}
