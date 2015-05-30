/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.perform;

import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.ChatChannel;
import pl.shg.arcade.api.channels.GlobalChannel;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class MessagePerform extends Perform {
    private ChatChannel channel;
    private String message;
    
    public MessagePerform(FileConfiguration config, String id, int seconds, boolean repeating, int times) {
        super("message", config, id, seconds, repeating, times);
    }
    
    @Override
    public void run() {
        this.getChannel().sendServerMessage(null, this.getMessage());
    }
    
    public ChatChannel getChannel() {
        return this.channel;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setChannel(ChatChannel channel) {
        this.channel = channel;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public static MessagePerform create(Module module, FileConfiguration config,
            String id, int seconds, boolean repeating, int times) {
        MessagePerform perform = new MessagePerform(config, id, seconds, repeating, times);
        perform.setChannel(getChannel(module, config, id, perform));
        perform.setMessage(Config.getValueMessage(config, module, id + ".perform." + perform.getName() + ".message", null, false));
        return perform;
    }
    
    private static ChatChannel getChannel(Module module, FileConfiguration config, String id, Perform perform) {
        String name = Config.getValueString(config, module, id + ".perform." + perform.getName() + ".channel", "global");
        ChatChannel channel = null;
        
        if (!name.equals("global")) {
            channel = Arcade.getTeams().getChannel(name);
        }
        
        if (channel == null) {
            channel = new GlobalChannel();
        }
        return channel;
    }
}
