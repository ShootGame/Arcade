/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.plugin;

import pl.shg.arcade.api.module.Module;

/**
 *
 * @author Aleksander
 */
public interface IRegistration {
    void register(Class<? extends Module> module);
}
