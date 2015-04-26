/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import java.util.Collection;

/**
 *
 * @author Aleksander
 */
public interface ModuleManager {
    void active(Module module);
    
    Module asObject(Class<? extends Module> module);
    
    Collection<Module> getActiveModules();
    
    void inactive(Module module);
    
    void inactiveAll();
    
    Class<? extends Module> getModuleExact(String id);
    
    Collection<Class<? extends Module>> getModules();
    
    void register(Class<? extends Module> module) throws ModuleException;
}
