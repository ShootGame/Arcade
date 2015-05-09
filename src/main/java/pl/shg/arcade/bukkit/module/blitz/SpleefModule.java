/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.blitz;

import java.util.Date;
import pl.shg.arcade.api.map.Tutorial;

/**
 *
 * @author Aleksander
 */
public class SpleefModule extends BlitzModule {
    public SpleefModule() {
        super();
        this.setDate(new Date(2015, 5, 9));
        this.setID("spleef");
        this.setVersion("1.0");
        
        this.clearExamples();
        this.deploy(true);
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Spleef",
                "Twoim zadaniem jest zabicie jak najwiecej graczy.\n\n" +
                "Wygrywa gracz który ostatni zostanie w meczu przeciwników.");
    }
}
