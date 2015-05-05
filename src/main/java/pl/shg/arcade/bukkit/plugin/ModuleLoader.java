/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.lang.reflect.Field;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.command.def.VariableCommand;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ModuleManager;
import pl.shg.arcade.bukkit.module.*;
import pl.shg.arcade.bukkit.module.blitz.BlitzModule;
import pl.shg.arcade.bukkit.module.deathmatch.DeathMatchModule;
import pl.shg.arcade.bukkit.module.escape.EscapeModule;
import pl.shg.arcade.bukkit.module.lib.Points;
import pl.shg.arcade.bukkit.module.monument.MonumentModule;
import pl.shg.arcade.bukkit.module.paintball.PaintballModule;
import pl.shg.arcade.bukkit.module.wool.WoolModule;

/**
 *
 * @author Aleksander
 */
public abstract class ModuleLoader implements IRegistration {
    public void init() {
        this.features();
        this.libraries();
        this.objectives();
    }
    
    private void features() {
        this.register(AntiGriefModule.class); // ess
        this.register(AutoJoinModule.class); // prefered for the blitz gamemodes
        this.register(AutoRespawnModule.class); // prefered for the blitz gamemodes
        this.register(BlockPearlTeleportModule.class);
        this.register(CancelDropModule.class);
        this.register(CancelPickupExpModule.class);
        this.register(CancelPickupModule.class);
        this.register(ChatModule.class); // ess
        this.register(DeathMessagesModule.class); // ess
        this.register(DelayedRespawnModule.class);
        this.register(DisableBedsModule.class); // ess
        this.register(DisableMobSpawningModule.class);
        this.register(FakePlayerDamageModule.class);
        this.register(FeatherFallingModule.class);
        this.register(GameRuleModule.class);
        this.register(JoinWhenRunningCancelModule.class); // prefered for the blitz gamemodes
        this.register(LivesModule.class); // prefered for the blitz gamemodes
        this.register(NoDeathDropsModule.class);
        this.register(NoHungerModule.class);
        this.register(NoPvpModule.class);
        this.register(NoRainModule.class);
        this.register(NoThunderModule.class);
        this.register(PlayableAreaModule.class); // ess
        this.register(RageModule.class);
        this.register(StaticChestItemsModule.class);
    }
    
    private void libraries() {
        this.register(Points.class);
    }
    
    private void objectives() {
        this.register(BlitzModule.class);
        this.register(DeathMatchModule.class);
        this.register(EscapeModule.class);
        this.register(MonumentModule.class);
        this.register(PaintballModule.class);
        this.register(WoolModule.class);
    }
    
    public static void registerVariableCommand() {
        VariableCommand.setPerformer(new VariableCommand.Performer() {
            private final ModuleManager modules = Arcade.getModules();
            
            @Override
            public void perform(Sender sender, String target, String[] args) {
                try {
                    if (!(target.endsWith("Module") || target.endsWith("module"))) {
                        target = target + "Module";
                    }
                    
                    Class<?> clazz = Class.forName("pl.shg.arcade.bukkit.module." + target);
                    Module result = null;
                    for (Module module : this.modules.getActiveModules()) {
                        if (module.getClass().equals(clazz)) {
                            result = module;
                            break;
                        }
                    }
                    
                    if (result == null) {
                        sender.sendError("Klasa " + clazz.getCanonicalName() + " nie jest modulem lub nie jest aktywnym modulem.");
                    } else if (args.length == 1) {
                        this.printList(sender, result);
                    } else if (args.length == 2) {
                        this.printInfo(sender, result, args[1]);
                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            builder.append(args[i]).append(" ");
                        }
                        String value = builder.toString().substring(0, builder.toString().length() - 1);
                        this.set(sender, result, args[1], Color.translate(value));
                    }
                } catch (ClassNotFoundException ex) {
                    sender.sendError("Nie znaleziono takiej klasy (" + ex.getMessage() + ").");
                }
            }
            
            private Field getField(Sender sender, Class<?> clazz, String field) {
                try {
                    Field f = clazz.getDeclaredField(field);
                    f.setAccessible(true);
                    return f;
                } catch (NoSuchFieldException ex) {
                    sender.sendError("Nie znaleziono zmiennej o nazwie \"" + field + "\".");
                } catch (SecurityException ex) {
                    sender.sendError("Nie mozna uzyskac dostepu do zmiennej \"" + field + "\" (moze jest final?).");
                }
                return null;
            }
            
            private void printInfo(Sender sender, Module module, String variable) {
                Field field = this.getField(sender, module.getClass(), variable);
                if (field != null) {
                    try {
                        sender.sendMessage(Color.GOLD + field.getName() + Color.GRAY + " -> " + Color.GREEN + field.get(module));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        sender.sendError("Wystapil blad przy próbie wyswietlenia informacji o zmiennej - " + ex.getMessage());
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
                if (field != null) {
                    try {
                        Class<?> type = field.getType();
                        if (type.equals(Boolean.class)) {
                            field.setBoolean(module, Boolean.parseBoolean(value));
                        } else if (type.equals(Byte.class)) {
                            field.setByte(module, Byte.parseByte(value));
                        } else if (type.equals(Character.class)) {
                            field.set(module, value.charAt(0));
                        } else if (type.equals(Double.class)) {
                            field.setDouble(module, Double.parseDouble(value));
                        } else if (type.equals(Float.class)) {
                            field.setFloat(module, Float.parseFloat(value));
                        } else if (type.equals(Integer.class)) {
                            field.setInt(module, Integer.parseInt(value));
                        } else if (type.equals(Long.class)) {
                            field.setLong(module, Long.parseLong(value));
                        } else if (type.equals(Short.class)) {
                            field.setShort(module, Short.parseShort(value));
                        } else {
                            field.set(module, value);
                        }
                        
                        sender.sendSuccess("Zmieniono wartosc dla zmiennej " + field.getName() + " na " + value + ".");
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        sender.sendError("Wystapil blad przy próbie ustawienia wartosci dla zmiennej - " + ex.getMessage());
                    }
                }
            }
        });
    }
}
