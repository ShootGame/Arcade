/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class WoolscapeParty extends Party {
    public WoolscapeParty() {
        super(new Date(2015, 5, 9), "woolscape", "Woolscape", Version.valueOf("1.0"));
        this.deploy(true);
    }
    
    @Override
    public void loadParty(File file) {
        FileConfiguration config = Config.get(file);
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Twoim zadaniem jest szybkie znalezienie koloru welny,",
            "przybiegniecie oraz zatrzymanie sie na niej.",
            "Welna znajduje sie znajduje sie w Twoim ekwipunku."
        };
    }
}
