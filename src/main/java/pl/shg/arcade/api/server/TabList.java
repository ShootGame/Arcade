/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

/**
 *
 * @author Aleksander
 */
public class TabList {
    private String footer, header;
    
    public String getFooter() {
        return this.footer;
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public boolean hasFooter() {
        return this.footer != null;
    }
    
    public boolean hasHeader() {
        return this.header != null;
    }
    
    public void setFooter(String footer) {
        this.footer = footer;
    }
    
    public void setHeader(String header) {
        this.header = header;
    }
}
