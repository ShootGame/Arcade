/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Tutorial {
    private final List<Page> pages = new ArrayList<>();
    
    public void addPage(Page page) {
        Validate.notNull(page, "page can not be null");
        if (!this.hasGameMode(page.getGameMode())) {
            this.pages.add(page);
        }
    }
    
    public String getName() {
        Map map = Arcade.getMaps().getCurrentMap();
        if (this.isEmpty()) {
            return Color.GRAY + Color.ITALIC + "Poradnik gry na mapie " +
                    map.getDisplayName() + Color.RESET + Color.RED + " (brak)";
        } else {
            return Color.GOLD + "Poradnik gry na mapie " + Color.RED + map.getDisplayName();
        }
    }
    
    public List<Page> getPages() {
        return this.pages;
    }
    
    public boolean hasGameMode(String gameMode) {
        Validate.notNull(this, gameMode);
        for (Page page : this.getPages()) {
            if (page.getGameMode().equals(gameMode)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEmpty() {
        return this.getPages().isEmpty();
    }
    
    public List<String> toList() {
        List<String> list = new ArrayList<>();
        for (Page page : this.getPages()) {
            list.add(Color.DARK_GREEN + Color.UNDERLINE + Color.BOLD + page.getGameMode()
                    + "\n\n" + Color.RESET + Color.RED + page.getText());
        }
        return list;
    }
    
    public static class Page {
        private final String gameMode;
        private String text;
        
        public Page(String gameMode, String text) {
            this.gameMode = gameMode;
            this.setText(text);
        }
        
        public String getGameMode() {
            return this.gameMode;
        }
        
        public String getText() {
            return this.text;
        }
        
        public final void setText(String text) {
            Validate.notNull(text, "text can not be null");
            this.text = text;
        }
    }
}
