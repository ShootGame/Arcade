/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.boss;

import pl.shg.commons.database.Connection;
import pl.shg.commons.documents.Document;
import pl.shg.commons.documents.DocumentInfo;

/**
 *
 * @author Aleksander
 */
@DocumentInfo(
        name = "obj_boss",
        strong = false,
        connection = Connection.USERS,
        helper = BossHelper.class
)
public class BossDocument extends Document {
    public BossDocument() {
        super();
    }
}
