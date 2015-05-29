/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.human;

import pl.shg.arcade.api.tablist.TabList;

/**
 *
 * @author Aleksander
 */
public interface Titleable {
    void sendSubtitle(String subtitle);
    
    void sendTitle(String title);
    
    void setTabList(TabList tabList);
}
