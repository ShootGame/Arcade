/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.ProxyServer;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BungeeCordProxy implements ProxyServer {
    public static final String NAME = "BungeeCord";
    
    @Override
    public void connect(Player player, String server) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(server, "server can not be null");
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);
        
        player.sendMessage(Color.YELLOW + "Laczenie z serwerem " + Color.GOLD + server + Color.YELLOW + "...");
        try {
            output.writeUTF("Connect");
            output.writeUTF(server.toLowerCase().replace(" ", "-"));
        } catch (IOException ex) {
            player.sendMessage(Color.RED + "Nastapil blad podczas laczenia z serwerem " + server + ": " + ex.getMessage());
            Logger.getLogger(BungeeCordProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ((org.bukkit.entity.Player) player.getPlayer()).sendPluginMessage(
                ArcadeBukkitPlugin.getPlugin(), this.getProxyName(), array.toByteArray());
    }
    
    @Override
    public String getProxyName() {
        return BungeeCordProxy.NAME;
    }
    
    public static void register(Server server) {
        Validate.notNull(server, "server can not be null");
        server.getMessenger().registerOutgoingPluginChannel(ArcadeBukkitPlugin.getPlugin(), BungeeCordProxy.NAME);
    }
}
