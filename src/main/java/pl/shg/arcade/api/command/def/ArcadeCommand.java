/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.command.def;

import com.google.gson.JsonSyntaxException;
import java.io.Reader;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.JSONResourceFile;
import pl.shg.arcade.api.util.ResourceFile;

/**
 *
 * @author Aleksander
 */
public class ArcadeCommand extends Command {
    private static String error;
    private static ResourceFile resource;
    
    public ArcadeCommand() {
        super(new String[] {"arcade"},
                "Glówne informacje o pluginie Arcade", "arcade");
        this.setPermission("arcade.command.arcade");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (resource == null) {
            createResource(sender);
        }
        
        if (!resource.isLoaded()) {
            sender.sendError("Nie udalo sie pobrac informacji (bledny plik?).");
            if (error != null) {
                sender.sendError(error);
            }
        } else {
            this.print(sender);
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private String getInfo(String name, String value) {
        return Color.YELLOW + name + Color.RESET + ": " + Color.BLUE;
    }
    
    private void print(Sender sender) {
        JSONResourceFile json = resource.getJSON();
        
        sender.sendMessage(Command.getTitle(json.getName(), null));
        sender.sendMessage(this.getInfo("wersja", json.getVersion()));
        sender.sendMessage(this.getInfo("commit", json.getCommit()));
        sender.sendMessage(this.getInfo("minecraft", json.getMinecraft()));
        sender.sendMessage(this.getInfo("skompliowano w kodowaniu", json.getEncoding()));
    }
    
    public static void createResource(Sender sender) {
        if (sender == null) {
            sender = Arcade.getCommands().getConsoleSender();
        }
        sender.sendError("Pobieranie informacji o pluginie Arcade...");
        
        resource = ResourceFile.newInstance();
        try {
            Reader reader = resource.getResourceReader();
            resource.load(resource.createReader(reader));
        } catch (JsonSyntaxException ex) {
            error = ex.getMessage();
        }
    }
    
    public static ResourceFile getResource() {
        return resource;
    }
}
