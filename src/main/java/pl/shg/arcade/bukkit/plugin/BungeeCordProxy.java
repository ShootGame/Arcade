/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import org.apache.commons.lang3.Validate;
import org.bukkit.Server;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.ProxyServer;
import pl.shg.commons.server.Servers;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class BungeeCordProxy implements ProxyServer {
    @Override
    public void connect(Player player, TargetServer server) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(server, "server can not be null");
        Servers.getProxy().connect((org.bukkit.entity.Player) player.getPlayer(), server);
    }
    
    @Override
    public String getProxyName() {
        return "BungeeCord";
    }
    
    public static void register(Server server) {}
}
