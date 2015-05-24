/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.commons.database.Connection;
import pl.shg.commons.documents.Document;
import pl.shg.commons.documents.DocumentInfo;

/**
 *
 * @author Aleksander
 */
@DocumentInfo(name = "obj_destroyable", strong = false, connection = Connection.USERS)
public class DestroyableDocument extends Document {
    public DestroyableDocument() {
        super();
    }
}
