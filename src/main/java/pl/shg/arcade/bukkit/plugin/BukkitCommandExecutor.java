/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import pl.shg.arcade.ArcadeCommandManager;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class BukkitCommandExecutor extends ArcadeCommandManager implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<Command> commands = Arcade.getCommands().getCommands();
        for (Command cmd : commands) {
            for (String alias : cmd.getCommands()) {
                if (alias.toLowerCase().equals(label.toLowerCase())) {
                    if (sender instanceof Player) {
                        this.execute(cmd, Arcade.getServer().getPlayer(((Player) sender).getUniqueId()), args);
                    } else {
                        this.execute(cmd, Arcade.getCommands().getConsoleSender(), args);
                    }
                    return true;
                }
            }
        }
        
        sender.sendMessage(Color.RED + "Nieznaleziono klasy wykonujacej dla komendy " + command.getName() + ".");
        sender.sendMessage(Color.RED + "Zglos to niezwlocznie do administracji serwera!");
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        return null;
    }
    
    @Override
    public boolean registerServerCommand(Command command) {
        org.bukkit.command.Command bukkitCommand = new CommandPerformer(this, command.getCommands()[0]);
        
        List<String> aliases = new ArrayList<>();
        for (int i = 1; i < command.getCommands().length; i++) {
            aliases.add(command.getCommands()[i]);
        }
        bukkitCommand.setAliases(aliases);
        bukkitCommand.setDescription(command.getDescription());
        bukkitCommand.setPermission(command.getPermission());
        bukkitCommand.setUsage(Command.buildUsage(command));
        
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            if (field != null) {
                field.setAccessible(true);
                CommandMap map = (CommandMap) field.get(Bukkit.getServer());
                map.register(command.getCommands()[0], ArcadeBukkitPlugin.PLUGIN_NAME.toLowerCase(), bukkitCommand);
            }
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalArgumentException
                | IllegalAccessException ex) {
            Logger.getLogger(BukkitCommandExecutor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    private void execute(Command command, Sender sender, String[] args) {
        if (command.hasPermission()) {
            if (!sender.hasPermission(command.getPermission())) {
                sender.sendError(Command.getErrorMessage(Command.CommandMessage.NO_PERMISSION));
                return;
            }
        }
        
        try {
            if (command.minArguments() > args.length) {
                sender.sendError(command.getDescription());
                sender.sendError(command.getUsage());
            } else {
                command.execute(sender, args);
            }
        } catch (CommandException ex) {
            sender.sendError(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            sender.sendError("Nie udalo sie wykonac komendy " + command.getCommands()[0] + " poniewaz wykryto naganny blad.");
            sender.sendMessage(Color.RED + "Zglos to niezwlocznie do administracji serwera!");
        }
    }
    
    public static void createHelpTopic() {
        Set<HelpTopic> help = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());
        for (Command command : Arcade.getCommands().getCommands()) {
            org.bukkit.command.Command bCommand = Bukkit.getCommandMap().getCommand(command.getCommands()[0]);
            if (bCommand != null) {
                help.add(new GenericCommandHelpTopic(bCommand));
            }
        }
        
        IndexHelpTopic index = new IndexHelpTopic(
                ArcadeBukkitPlugin.PLUGIN_NAME,
                "Wszystkie komendy mini-gier " + ArcadeBukkitPlugin.PLUGIN_NAME,
                null,
                help,
                "Ponizej znajduja sie komendy " + ArcadeBukkitPlugin.PLUGIN_NAME + " do których posiadasz uprawnienia:"
        );
        
        Bukkit.getHelpMap().addTopic(index);
    }
    
    private class CommandPerformer extends org.bukkit.command.Command {
        private final BukkitCommandExecutor executor;
        
        public CommandPerformer(BukkitCommandExecutor executor, String command) {
            super(command);
            this.executor = executor;
        }
        
        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return this.executor.onCommand(sender, this, label, args);
        }
        
        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
            return this.executor.onTabComplete(sender, this, alias, args);
        }
    }
}
