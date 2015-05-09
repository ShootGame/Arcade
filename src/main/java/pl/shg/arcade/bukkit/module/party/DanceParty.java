/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.util.Date;

/**
 *
 * @author Aleksander
 */
public class DanceParty extends Party {
    public DanceParty() {
        super(new Date(2015, 5, 9), "dance", "1.0");
    }
    
    @Override
    public String[] getPartyTutorial() {
        return new String[] {
            "Twoim zadaniem jest szybkie znalezienie koloru welny, który obecnie znajduje sie w Twoim ekwipunku."
        };
    }
}
