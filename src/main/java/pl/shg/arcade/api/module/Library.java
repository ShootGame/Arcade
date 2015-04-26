/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.module;

import java.util.Date;
import pl.shg.arcade.api.module.docs.LibDeprecation;

/**
 *
 * @author Aleksander
 */
public abstract class Library extends ObjectiveModule {
    public Library(Date date, String id, String version) {
        super(date, "lib-" + id, version);
        this.getDocs().setDeprecation(new LibDeprecation());
    }
}
