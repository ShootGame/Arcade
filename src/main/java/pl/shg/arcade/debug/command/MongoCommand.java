/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.command;

import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.commons.mongo.Database;
import pl.shg.commons.mongo.Mongo;
import pl.shg.commons.mongo.ServersBase;
import pl.shg.commons.time.UnixTime;

/**
 *
 * @author Aleksander
 */
public class MongoCommand extends Command {
    public MongoCommand() {
        super(new String[] {"mongo", "database", "db"},
                "Testowanie bazy danych MongoDB", "mongo [command]");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            this.showHelp(sender);
        } else {
            args[0] = args[0].toLowerCase();
            this.perform(sender, args);
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void perform(Sender sender, String[] args) {
        switch (args[0]) {
            case "connect":
                UnixTime init = UnixTime.now();
                
                sender.sendMessage("Laczenie z MongoDB (Mongo)...");
                Database.initializeMongo(Mongo.URI, Mongo.DATABASE);
                
                sender.sendMessage("Laczenie z MongoDB (Servers)...");
                Database.initializeServers(ServersBase.URI, ServersBase.DATABASE, ServersBase.COLLECTION);
                
                long ms = UnixTime.now().getTime() - init.getTime();
                sender.sendMessage("Polaczono z bazami danych MongoDB. Zajelo " + ms + " ms.");
                break;
            
            case "mongo":
                this.performMongo(sender, args);
                break;
            
            case "servers":
                this.performServers(sender, args);
                break;
            
            default:
                sender.sendError("Nieprawidlowe polecenie.");
                break;
        }
    }
    
    private void performMongo(Sender sender, String[] args) {
        
    }
    
    private void performServers(Sender sender, String[] args) {
        
    }
    
    private void showHelp(Sender sender) {
        
    }
}
