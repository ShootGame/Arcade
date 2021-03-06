/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import pl.shg.arcade.ArcadePlayer;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.PlayerReceiveChatEvent;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.tablist.TabList;
import pl.shg.arcade.api.team.TeamColor;
import pl.shg.arcade.api.text.ActionMessageType;
import pl.shg.arcade.api.text.BossBarMessage;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.commons.bukkit.UserUtils;
import pl.shg.commons.users.BukkitUser;
import pl.shg.commons.users.LocalUser;
import pl.shg.commons.util.Messages;
import pl.shg.commons.util.Tablists;
import pl.shg.commons.util.Titles;
import pl.themolka.permissions.Group;
import pl.themolka.permissions.User;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Aleksander
 */
public class BukkitPlayer extends ArcadePlayer {
    private static final CraftServer server = (CraftServer) Bukkit.getServer();
    private final BukkitUser commons;
    private final User permissions;
    private final CraftPlayer player;
    
    public BukkitPlayer(Player player) {
        Validate.notNull(player, "player can not be null");
        this.commons = UserUtils.getUser(player);
        this.permissions = new User(player);
        this.player = (CraftPlayer) player;
        
        this.makePermissions();
    }
    
    @Override
    public boolean canSee(pl.shg.arcade.api.human.Player player) {
        Validate.notNull(player, "player can not be null");
        return Arcade.getPlayerManagement().getVisibility().canSee(this, player);
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
    public LocalUser getCommons() {
        return this.commons;
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
        return BukkitLocation.convert(this.player.getLocation());
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
    public int hashCode() {
        return Objects.hash(this.getUUID());
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
        this.player.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
        ((Player) this.player).spigot().respawn();
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
        this.player.teleport(BukkitLocation.valueOf(location));
    }
    
    @Override
    public void updateTag() {
        ScoreboardManager.updateTag(this);
    }
    
    @Override
    public void updateVisibility() {
        Visibility.update(this);
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
    
    public void sendChatPacket(String message, byte type) {
        for (IChatBaseComponent component : CraftChatMessage.fromString(message)) {
            this.sendPacket(new PacketPlayOutChat(component, type));
        }
    }
    
    public void sendPacket(Packet packet) {
        if (this.bukkit() != null && this.bukkit().getHandle() != null) {
            this.getCommons().sendPacket(packet);
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
