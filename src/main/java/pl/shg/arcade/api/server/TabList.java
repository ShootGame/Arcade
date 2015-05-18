/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pl.shg.arcade.api.util.Validate;
import pl.shg.commons.util.TabCell;
import pl.shg.commons.util.Tablists;

/**
 *
 * @author Aleksander
 */
public class TabList {
    private final List<TabCell> cells = new ArrayList<>();
    private String footer, header;
    
    public void addCell(TabCell cell) {
        Validate.notNull(cell, "cell can not be null");
        this.cells.remove(cell);
        this.cells.add(cell);
    }
    
    public TabCell getCell(int column, int line) {
        Validate.notNegative(column, "column can not be negative");
        Validate.notNegative(line, "line can not be negative");
        
        for (TabCell cell : this.cells) {
            if (cell.getColumn() == column && cell.getLine() == line) {
                return cell;
            }
        }
        
        TabCell cell = new TabCell(column, line, "", new GameProfile(UUID.randomUUID(), ""), Tablists.PING);
        this.cells.add(cell);
        return cell;
    }
    
    public List<TabCell> getCells() {
        return this.cells;
    }
    
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
