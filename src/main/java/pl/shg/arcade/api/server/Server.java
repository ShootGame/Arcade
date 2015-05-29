/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.arcade.api.tablist.TabList;

/**
 *
 * @author Aleksander
 */
public interface Server extends MinecraftServer {
    void broadcast(String message);
    
    boolean bungeeCord();
    
    void checkEndMatch();
    
    TabList getGlobalTabList();
    
    SchedulerManager getScheduler();
    
    void setGlobalTabList(TabList tabList);
    
    boolean isDev();
}
