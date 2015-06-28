/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.plugin;

import java.lang.reflect.Field;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.command.def.VariableCommand;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ModuleManager;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class PerformerImpl implements VariableCommand.Performer {
    public static final String PACKAGE = "pl.shg.arcade.bukkit.module.";
    private final ModuleManager modules = Arcade.getModules();
    
    @Override
    public void perform(Sender sender, String target, String[] args) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + target);
            Module result = null;
            for (Module module : this.modules.getActiveModules()) {
                if (module.getClass().equals(clazz)) {
                    result = module;
                    break;
                }
            }
            
            if (result == null) {
                this.error(sender, "Klasa " + clazz.getCanonicalName() + " nie jest modulem lub nie jest aktywna.");
            } else if (args.length == 1) {
                this.printList(sender, result);
            } else if (args.length == 2) {
                this.printInfo(sender, result, args[1]);
            } else {
                String value = null;
                if (args[2] != null) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    value = Color.translate(builder.toString().substring(0, builder.toString().length() - 1));
                }
                this.set(sender, result, args[1], value);
            }
        } catch (ClassNotFoundException ex) {
            this.error(sender, "Nie znaleziono takiej klasy (" + ex.getMessage() + ").");
        }
    }
    
    private void error(Sender sender, String error) {
        sender.sendError(error.replace(PACKAGE, "*"));
    }
    
    private Field getField(Sender sender, Class<?> clazz, String field) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException ex) {
            this.error(sender, "Nie znaleziono zmiennej o nazwie \"" + field + "\".");
        } catch (SecurityException ex) {
            this.error(sender, "Nie mozna uzyskac dostepu do zmiennej \"" + field + "\".");
        }
        return null;
    }
    
    private void printInfo(Sender sender, Module module, String variable) {
        Field field = this.getField(sender, module.getClass(), variable);
        if (field != null) {
            try {
                sender.sendMessage(Color.GOLD + field.getName() + Color.GRAY + " -> " + Color.GREEN + field.get(module));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                this.error(sender, "Wystapil blad przy próbie wyswietlenia informacji o zmiennej - " + ex.getMessage());
            }
        }
    }
    
    private void printList(Sender sender, Module module) {
        for (Field field : module.getClass().getDeclaredFields()) {
            sender.sendMessage(Color.GOLD + field.getName());
        }
    }
    
    private void set(Sender sender, Module module, String variable, String value) {
        Field field = this.getField(sender, module.getClass(), variable);
        if (field == null) {
            return;
        }
        
        try {
            if (value == null) {
                field.set(module, null);
                sender.sendSuccess("Usunieto wartosc zmiennej " + field.getName() + ".");
                return;
            }
            
            Class<?> type = field.getType();
            if (type.equals(Boolean.TYPE)) {
                field.setBoolean(module, Boolean.parseBoolean(value));
            } else if (type.equals(Byte.TYPE)) {
                field.setByte(module, Byte.parseByte(value));
            } else if (type.equals(Character.TYPE)) {
                field.set(module, value.charAt(0));
            } else if (type.equals(Double.TYPE)) {
                field.setDouble(module, Double.parseDouble(value));
            } else if (type.equals(Float.TYPE)) {
                field.setFloat(module, Float.parseFloat(value));
            } else if (type.equals(Integer.TYPE)) {
                field.setInt(module, Integer.parseInt(value));
            } else if (type.equals(Long.TYPE)) {
                field.setLong(module, Long.parseLong(value));
            } else if (type.equals(Short.TYPE)) {
                field.setShort(module, Short.parseShort(value));
            } else {
                field.set(module, value);
            }
            
            sender.sendSuccess("Zmieniono wartosc dla zmiennej " + field.getName() + " na " + field.get(module) + ".");
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            this.error(sender, "Wystapil blad przy próbie ustawienia wartosci dla zmiennej - " + ex.getMessage());
        }
    }
}
