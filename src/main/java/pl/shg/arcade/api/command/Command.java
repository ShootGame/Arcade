/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command;

import java.util.HashMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Plugin;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class Command {
    private final String[] commands;
    private final String description;
    private final char[] flags;
    private final HashMap<String, String> options;
    private String permission;
    private String usage;
    
    public enum CommandMessage {
        NO_PERMISSION, PLAYER_NEEDED, NUMBER_NEEDED, CONSOLE_NEEDED;
    }
    
    public Command(String[] commands, String description, String usage) {
        this(commands, description, usage, null);
    }
    
    public Command(String[] commands, String description, String usage, char[] flags) {
        Validate.notNull(commands, "commands can not be null");
        if (commands.length == 0) {
            throw new IllegalArgumentException("commands can not be empty");
        } else if (description == null) {
            description = Color.ITALIC + "Brak dostepnego opisu komendy.";
        }
        this.commands = commands;
        this.description = description;
        this.flags = flags;
        this.options = new HashMap<>();
        this.setUsage(usage);
    }
    
    public boolean canNot(Sender sender, String permission) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(permission, "permission can not be null");
        if (!sender.hasPermission(permission)) {
            this.throwMessage(sender, CommandMessage.NO_PERMISSION);
            return true;
        } else {
            return false;
        }
    }
    
    public abstract void execute(Sender sender, String[] args) throws CommandException;
    
    public String[] getCommands() {
        return this.commands;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getFlagValue(String[] args, char flag) {
        return this.getFlagValue(args, flag, null);
    }
    
    public String getFlagValue(String[] args, char flag, String def) {
        Validate.notNull(args, "args can not be null");
        Validate.notNull(flag, "flag can not be null");
        for (String arg : args) {
            arg = arg.toLowerCase();
            String split = null;
            if (arg.startsWith("-" + flag)) {
                if (arg.contains("=")) {
                    split = "=";
                } else if (arg.contains(":")) {
                    split = ":";
                }
            }
            
            if (split != null) {
                String[] list = arg.split(split, 2);
                if (list.length > 1) {
                    return list[1];
                }
            }
            return def;
        }
        return def;
    }
    
    public char[] getFlags() {
        return this.flags;
    }
    
    public HashMap<String, String> getOptions() {
        return this.options;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public String getStringFromArgs(int index, String[] args) {
        Validate.notNegative(index, "index must be positive (or zero)");
        Validate.notNull(args, "args can not be null");
        StringBuilder builder = new StringBuilder();
        for (int i = index; i < args.length; i++) {
            if (!this.isFlag(args[i].toLowerCase())) {
                builder.append(args[i]).append(" ");
            }
        }
        
        try {
            return builder.toString().substring(0, builder.toString().length() - 1);
        } catch (StringIndexOutOfBoundsException ex) {
            return builder.toString();
        }
    }
    
    public Plugin getPlugin() {
        return Arcade.getPlugin();
    }
    
    public String getUsage() {
        return "/" + this.usage;
    }
    
    public boolean hasFlag(String[] args, char flag) {
        Validate.notNull(args, "args can not be null");
        Validate.notNull(flag, "flag can not be null");
        for (String arg : args) {
            if (arg.toLowerCase().equals("-" + flag)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasOptions() {
        return !this.options.isEmpty();
    }
    
    public boolean hasPermission() {
        return this.permission != null;
    }
    
    public boolean isFlag(String arg) {
        if (!arg.toLowerCase().startsWith("-") || this.getFlags() == null) {
            return false;
        }
        for (char flag : this.getFlags()) {
            if (arg.substring(1).equalsIgnoreCase(String.valueOf(flag))) {
                return true;
            }
        }
        return false;
    }
    
    public abstract int minArguments();
    
    public int parseInteger(String string, int def) {
        Validate.notNull(string, "string can not be null");
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return def;
        }
    }
    
    public void setOption(String option, String description) {
        this.options.put(option, description);
    }
    
    public void setPermission(String permission) {
        this.permission = permission;
    }
    
    public final void setUsage(String usage) {
        if (usage == null) {
            usage = "/" + this.commands[0];
        }
        this.usage = usage;
    }
    
    public void throwMessage(Sender sender, CommandMessage message) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(message, "message can not be null");
        String result = getErrorMessage(message);
        if (result != null) {
            sender.sendError(result);
        }
    }
    
    public static String buildUsage(Command command) {
        StringBuilder builder = new StringBuilder();
        builder.append(command.getUsage());
        
        if (command.hasOptions()) {
            builder.append("\nLegenda:");
            for (String option : command.getOptions().keySet()) {
                builder.append("\n").append(option).append(" - ").append(command.getOptions().get(option));
            }
        }
        return builder.toString();
    }
    
    public static String getErrorMessage(CommandMessage message) {
        Validate.notNull(message, "message can not be null");
        switch (message) {
            //case NO_PERMISSION: return "Nie posiadasz uprawnien do tej komendy (lub podanego argumentu).";
            case NO_PERMISSION: return "Brak odpowiednich uprawnien.";
            case PLAYER_NEEDED: return "Bledny wysylajacy; ta komende musi wykonac gracz.";
            case NUMBER_NEEDED: return "Podany przez Ciebie argument musi byc liczba!";
            case CONSOLE_NEEDED: return "Bledny wysylajacy; ta komende trzeba wykonac z konsoli serwera.";
            default: return null;
        }
    }
    
    public static String getTitle(String title, String info) {
        Validate.notNull(title, "title can not be null");
        String label = Color.RED + Color.STRIKETHROUGH + "-------------" + Color.RESET;
        if (info == null) {
            info = "";
        } else {
            info = info + " " + Color.RESET;
        }
        return label + " " + Color.DARK_AQUA + title + Color.RESET + " " + info + label;
    }
}
