/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.deathmatch;

import java.util.Date;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public class PaintballModule extends DeathMatchModule {
    public PaintballModule() {
        super();
        this.setDate(new Date(2015, 4, 26));
        this.setID("paintball");
        this.setVersion(Version.valueOf("1.0"));
        
        this.clearExamples();
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.INT) {
            @Override
            public String getPrefix() {
                return "Ustaw ilość punktów (zabić) po ilu mecz ma się zakończyć, " +
                        "a drużyna wygrać. Domyślnie jest to 300 punktów.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "paintball:",
                    "  max-score: 300"
                };
            }
        });
        this.deploy(true);
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Paintball",
                "Zadaniem Twojej druzyny jest zabicie jak najwiecej graczy z druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna która zabije " + this.maxScore + " przeciwników.");
    }
}
