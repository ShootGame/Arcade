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
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class SheepsParty extends Party {

    public SheepsParty() {
        super(new Date(2015, 5, 9), "sheeps", "Capture the sheep", "1.0");
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Zanies na swój spawn najwiecej owiec oraz je ochron od przeciwników."
        };
    }
    
    @Override
    public void loadParty(File file) {
        FileConfiguration config = Config.get(file);
    }
}
