/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.util.UUID;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import pl.shg.arcade.ArcadePlayer;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.PlayerReceiveChatEvent;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.team.TeamColor;
import pl.shg.arcade.api.tablist.TabList;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.BossBarMessage;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.commons.util.ClientSettings;
import pl.shg.commons.util.Messages;
import pl.shg.commons.util.Tablists;
import pl.shg.commons.util.Titles;
import pl.themolka.permissions.Group;
import pl.themolka.permissions.User;

/**
 *
 * @author Aleksander
 */
public class BukkitPlayer extends ArcadePlayer {
    private static final CraftServer server = (CraftServer) Bukkit.getServer();
    private final EntityPlayer handle;
    private final User permissions;
    private final CraftPlayer player;
    private final ClientSettings settings;
    
    public BukkitPlayer(Player player) {
        Validate.notNull(player, "player can not be null");
        this.permissions = new User(player);
        this.player = (CraftPlayer) player;
        this.handle = this.player.getHandle();
        this.settings = ClientSettings.newInstance(this.player);
        
        this.makePermissions();
    }
    
    @Override
    public void close() {
        this.player.closeInventory();
    }
    
    @Override
    public void damage(double amount) {
        Validate.isTrue(amount >= 0);
        this.setHealth(this.getHealth() - amount);
    }
    
    @Override
    public void disconnect(String reason) {
        this.player.kickPlayer(reason);
    }
    
    @Override
    public ClientSettings getClientSettings() {
        return this.settings;
    }
    
    @Override
    public int getFeedLevel() {
        return this.player.getFoodLevel();
    }
    
    @Override
    public void setFeedLevel(int feed) {
        Validate.isTrue(feed >= 0);
        this.player.setFoodLevel(feed);
    }
    
    @Override
    public double getHealth() {
        return this.player.getHealth();
    }
    
    @Override
    public void setHealth(double health) {
        this.player.setHealth(health);
    }
    
    @Override
    public Location getLocation() {
        org.bukkit.Location location = this.player.getLocation();
        return new Location(location.getX(), location.getY(), location.getZ());
    }
    
    @Override
    public String getName() {
        return this.player.getName();
    }
    
    @Override
    public User getPermissions() {
        return this.permissions;
    }
    
    /**
     * @deprecated use #bukkit() instead
     */
    @Deprecated
    @Override
    public Object getPlayer() {
        return this.player;
    }
    
    @Override
    public UUID getUUID() {
        return this.player.getUniqueId();
    }
    
    @Override
    public boolean hasPermission(String permission) {
        Validate.notNull(permission, "permission can not be null");
        return this.player.hasPermission(permission);
    }
    
    @Override
    public boolean isDead() {
        return this.player.isDead();
    }
    
    @Override
    public boolean kickToLobby(String reason) {
        this.disconnect(reason);
        return true;
    }
    
    @Override
    public void reloadPermissions() {
        this.player.recalculatePermissions();
    }
    
    @Override
    public void resetPlayerState() {
        Arcade.getPlayerManagement().setAsObserver(this, true, true, true);
    }
    
    @Override
    public void respawn() {
        this.player.getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    }
    
    @Override
    public void sendActionMessage(ActionMessageType type, String message) {
        Validate.notNull(type, "type can not be null");
        Validate.notNull(message, "message can not be null");
        Messages.sendAction(this.player, message);
        this.sendMessage(message);
    }
    
    @Override
    public void sendBossBarMessage(BossBarMessage message) {
        Validate.notNull(message, "message can be null");
        // TOOD manage the dragon packet
    }
    
    @Override
    public void sendChatMessage(Sender sender, ChatMessage message) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(message, "message can not be null");
        
        PlayerReceiveChatEvent event = new PlayerReceiveChatEvent(this, sender, message);
        Event.callEvent(event);
        if (!event.isCancel()) {
            Messages.sendChat(this.player, event.getMessage().getSource());
        }
    }
    
    @Override
    public void sendMessage(String message) {
        Validate.notNull(message, "message can not be null");
        Messages.sendMessage(this.player, message);
    }
    
    @Override
    public void sendSubtitle(String subtitle) {
        Validate.notNull(subtitle, "subtitle can not be null");
        Titles.send(this.player, null, subtitle);
    }
    
    @Override
    public void sendTitle(String title) {
        Validate.notNull(title, "title can not be null");
        Titles.send(this.player, title, null);
    }
    
    @Override
    public void setTabList(TabList tabList) {
        Validate.notNull(tabList, "tabList can not be null");
        Tablists.sendHeaderFooter(this.player, tabList.getHeader(), tabList.getFooter()); // send header and footer
    }
    
    @Override
    public void teleport(Location location) {
        Validate.notNull(location, "location can not be null");
        String map = Arcade.getMaps().getCurrentMap().getName();
        this.player.teleport(new org.bukkit.Location(Bukkit.getWorld(map),
                location.getX(), location.getY(), location.getZ()));
    }
    
    @Override
    public void teleport(Spawn spawn) {
        Validate.notNull(spawn, "spawn can not be null");
        String map = Arcade.getMaps().getCurrentMap().getName();
        this.player.teleport(new org.bukkit.Location(Bukkit.getWorld(map),
                spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch()));
    }
    
    @Override
    public void updateTag() {
        ScoreboardManager.updateTag(this);
    }
    
    public CraftPlayer bukkit() {
        return this.player;
    }
    
    public Color getBukkitColor(TeamColor color) {
        return Color.fromRGB(color.getRGB()[0], color.getRGB()[1], color.getRGB()[2]);
    }
    
    public final void makePermissions() {
        ((BukkitPermissionsManager) Arcade.getPermissions()).setGroupsFor(this.permissions);
        
        StringBuilder builder = new StringBuilder();
        for (Group group : this.getPermissions().getGroups()) {
            if (group.hasPrefix()) {
                builder.append(group.getPrefix()).append(pl.shg.arcade.api.text.Color.RESET);
            }
        }
        this.setChatPrefixes(builder.toString());
    }
    
    public synchronized void sendChatPacket(String message, byte type) {
        for (IChatBaseComponent component : CraftChatMessage.fromString(message)) {
            this.sendPacket(new PacketPlayOutChat(component, type));
        }
    }
    
    public synchronized void sendPacket(Packet packet) {
        if (this.bukkit() != null && this.bukkit().getHandle() != null) {
            this.handle.playerConnection.sendPacket(packet);
        }
    }
    
    public IChatBaseComponent toJSONText(String message) {
        IChatBaseComponent component = ChatSerializer.a(JSON.textOf(message));
        return CraftChatMessage.fixComponent(component);
    }
    
    public static BukkitPlayer valueOf(UUID uuid) {
        Validate.notNull(uuid, "uuid can not be null");
        
        pl.shg.arcade.api.human.Player player = Arcade.getServer().getPlayer(uuid);
        if (player != null) {
            return (BukkitPlayer) player;
        } else {
            return null;
        }
    }
}
