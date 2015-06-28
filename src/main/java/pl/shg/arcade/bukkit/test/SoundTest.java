/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.test;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.development.TestCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.bukkit.BukkitLocation;

/**
 *
 * @author Aleksander
 */
public class SoundTest extends TestCommand.Test {
    public static final float VOLUME = 5F;
    public static final float PITCH = 1F;
    
    public SoundTest() {
        super("sound", "[id|name]", false, "dzwieki Bukkit");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            this.list(sender);
        } else {
            try {
                int index = Integer.parseInt(args[1]);
                if (index < 0 || index >= this.getSounds().length) {
                    sender.sendError("Liczba ID jest zbyt wysoka lub zbyt niska.");
                } else {
                    this.play((Player) sender, this.getSounds()[index]);
                }
                return;
            } catch (NumberFormatException ex) {}
            
            try {
                Sound sound = Sound.valueOf(args[1].toUpperCase());
                this.play((Player) sender, sound);
            } catch (IllegalArgumentException ex) {
                sender.sendError("Nie znaleziono zadnego dzwieku o nazwie \"" + args[1].toUpperCase() + "\".");
            }
        }
    }
    
    public Sound[] getSounds() {
        return Sound.values();
    }
    
    public void play(Player player, Sound sound) {
        player.sendSuccess("Odtwarzanie \"" + sound.name() + "\" (ID " + sound.ordinal() + ")...");
        ((CraftPlayer) player.getPlayer()).playSound(BukkitLocation.valueOf(player.getLocation()), sound, VOLUME, PITCH);
    }
    
    private void list(Sender sender) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.getSounds().length; i++) {
            builder.append(i).append(": ").append(this.getSounds()[i].name()).append(", ");
        }
        sender.sendMessage(Color.YELLOW + builder.toString().substring(0, builder.toString().length() - 2));
    }
}
