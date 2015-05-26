/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.channels;

import java.util.logging.Level;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class GlobalChat extends ChatChannel {
    public GlobalChat() {
        super(false);
    }
    
    @Override
    public String getFormat(String[] args) {
        Validate.notNull(args, "args can not be null");
        return Color.WHITE + "[" + Color.AQUA + "G" + Color.WHITE + "] " +
                args[0] + Color.RESET + Color.GRAY + ": " + args[1];
    }
    
    @Override
    public void sendActionMessage(ActionMessageType type, String message) {
        Validate.notNull(type, "type can not be null");
        Validate.notNull(message, "message can not be null");
        
        Log.log(Level.INFO, "[Action] " + message);
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            player.sendActionMessage(type, message);
        }
    }
    
    @Override
    public void sendServerMessage(Sender sender, String message) {
        Validate.notNull(message, "message can not be null");
        Log.log(Level.INFO, "[Chat] " + message);
        if (sender == null) {
            sender = Arcade.getCommands().getConsoleSender();
        }
        
        ChatMessage chat = new ChatMessage();
        chat.setSender(sender);
        chat.setText(message);
        
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            player.sendChatMessage(sender, chat);
        }
    }
    
    @Override
    public boolean testSpy(Sender sender, Player reciver) {
        return false;
    }
}
