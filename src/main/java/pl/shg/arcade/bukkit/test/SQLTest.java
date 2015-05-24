/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.test;

import java.sql.ResultSet;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.server.development.TestCommand;
import pl.shg.commons.database.FutureTask;
import pl.shg.commons.documents.Documents;
import pl.shg.commons.users.UserDocument;

/**
 *
 * @author Aleksander
 */
public class SQLTest extends TestCommand.Test {
    public SQLTest() {
        super("sql", "<query>", true, "wykonaj zapytanie SQL do bazy danych");
    }
    
    @Override
    public void execute(final Sender sender, String[] args) throws CommandException {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(" ").append(args[i]);
        }
        String query = builder.toString().substring(1);
        
        Documents.of(UserDocument.class).query(query, null, new FutureTask() {
            @Override
            public void success(ResultSet result) {
                sender.sendSuccess("Query OK.");
            }
        });
    }
    
    @Override
    public int minArguments() {
        return 2;
    }
}
