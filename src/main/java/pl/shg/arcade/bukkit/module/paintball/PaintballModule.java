/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.paintball;

import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.bukkit.module.deathmatch.DeathMatchModule;

/**
 *
 * @author Aleksander
 */
public class PaintballModule extends DeathMatchModule {
    public PaintballModule() {
        super();
        this.deploy(true);
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Paintball",
                "Zadaniem Twojej druzyny jest zabicie jak najwiecej graczy z druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna która zabije " + this.maxScore + " przeciwników.");
    }
}
