/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.escape;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.v1_8_R1.PacketPlayOutExplosion;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Direction;
import pl.shg.arcade.api.match.MatchManager;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.bukkit.BukkitPlayer;

/**
 *
 * @author Aleksander
 */
public class Explosion {
    private static final MatchManager matches = Arcade.getMatches();
    
    private final Random random = new Random();
    private final World world;
    private Direction direction;
    
    public Explosion() {
        this.world = Bukkit.getWorld(Arcade.getMaps().getCurrentMap().getName());
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    // will return true if the border was detected
    public boolean removeBlocks() {
        Direction dir = this.getDirection();
        Location max = new Location(null, dir.getX() + 2, dir.getStaticY() - 1, dir.getZ() + 2);
        Location min = new Location(null, dir.getX() - 2, dir.getStaticY() - 3, dir.getZ() - 2);
        
        boolean result = false;
        for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    Block block = new Location(this.world, x, y, z).getBlock();
                    if (block.getType() == Material.BEDROCK || block.getType() == Material.OBSIDIAN) { // the border
                        result = true;
                    } else if (block.getType() != Material.AIR) {
                        block.setType(Material.STAINED_GLASS);
                        block.setData(DyeColor.RED.getData());
                    }
                }
            }
        }
        return result;
    }
    
    public void rotate() {
        float yaw = this.getDirection().getYaw();
        if (yaw > 0) {
            yaw -= 180;
        } else {
            yaw += 180;
        }
        this.direction.setYaw(yaw);
    }
    
    public void sendPacket() {
        PacketPlayOutExplosion packet = new PacketPlayOutExplosion(this.getDirection().getX(),
                this.getDirection().getY(), this.getDirection().getZ(), 0F, new ArrayList<>(), null);
        
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            ((BukkitPlayer) player).sendPacket(packet);
        }
        
        boolean border = this.removeBlocks();
        if (border) {
            this.rotate();
        }
        
        if (matches.getStatus() == MatchStatus.PLAYING) {
            this.updateDirection(!border);
        }
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void updateDirection(boolean generate) {
        Direction dir = this.getDirection();
        
        // generate the YAW position
        float yaw = dir.getYaw();
        if (generate && this.random.nextBoolean()) {
            float min = yaw - 40;
            float max = yaw + 40;
            dir.setYaw(this.random.nextFloat() * (max - min) + min);
        }
        
        // setup
        Vector vector = new Vector();
        double rotX = dir.getYaw();
        double rotY = dir.getPitch();
        
        vector.setY(-Math.sin(Math.toRadians(rotY)));
        double xz = Math.cos(Math.toRadians(rotY));
        
        vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
        vector.setZ(xz * Math.cos(Math.toRadians(rotX)));
        
        vector.normalize().multiply(-1);
        dir.setX(dir.getX() - vector.getX());
        dir.setZ(dir.getZ() - vector.getZ());
    }
}
