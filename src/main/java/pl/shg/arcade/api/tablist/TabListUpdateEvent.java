/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tablist;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.event.CancelableEvent;

/**
 *
 * @author Aleksander
 */
public class TabListUpdateEvent extends CancelableEvent {
    private TabList tabList;
    
    public TabListUpdateEvent(TabList tabList) {
        super(TabListUpdateEvent.class);
        this.setTabList(tabList);
    }
    
    public TabList getTabList() {
        return this.tabList;
    }
    
    public final void setTabList(TabList tabList) {
        Validate.notNull(tabList, "tabList can not be null");
        this.tabList = tabList;
    }
}
