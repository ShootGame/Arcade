/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.CommandManager;
import pl.shg.arcade.api.command.ConsoleSender;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.command.def.*; // commands
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class ArcadeCommandManager implements CommandManager {
    private final List<Command> commands;
    private final ConsoleSender console;
    
    public ArcadeCommandManager() {
        this.commands = new ArrayList<>();
        this.console = new ConsoleSender();
        this.registerDefaultCommands();
    }
    
    @Override
    public List<Command> getCommands() {
        return this.commands;
    }
    
    @Override
    public ConsoleSender getConsoleSender() {
        return console;
    }
    
    @Override
    public boolean perform(String command, Sender sender, String[] args) {
        Validate.notNull(command, "command can not be null");
        if (sender == null) {
            sender = this.getConsoleSender();
        }
        if (args == null) {
            args = new String[0];
        }
        
        for (Command cmd : this.commands) {
            if (cmd.getCommands()[0].toLowerCase().equals(command.toLowerCase())) {
                try {
                    if (!cmd.hasPermission() || (cmd.hasPermission() && sender.hasPermission(cmd.getPermission()))) {
                        cmd.execute(sender, args);
                    } else {
                        sender.sendError(Command.getErrorMessage(Command.CommandMessage.NO_PERMISSION));
                    }
                } catch (CommandException ex) {
                    sender.sendError(ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    sender.sendError("Nie udalo sie wykonac komendy " + cmd.getCommands()[0] + " poniewaz wykryto naganny blad.");
                    sender.sendMessage(Color.RED + "Zglos to do niezwlocznie administracji serwera!");
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean performAlias(String alias, Sender sender, String[] args) {
        Validate.notNull(alias, "alias can not be null");
        if (sender == null) {
            sender = this.getConsoleSender();
        }
        if (args == null) {
            args = new String[0];
        }
        
        for (Command command : this.commands) {
            for (String cmd : command.getCommands()) {
                if (cmd.toLowerCase().equals(alias.toLowerCase())) {
                    try {
                        if (!command.hasPermission() || (command.hasPermission() && sender.hasPermission(command.getPermission()))) {
                            command.execute(sender, args);
                        } else {
                            sender.sendError(Command.getErrorMessage(Command.CommandMessage.NO_PERMISSION));
                        }
                    } catch (CommandException ex) {
                        sender.sendError(ex.getMessage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        sender.sendError("Nie udalo sie wykonac komendy " + command.getCommands()[0] + " poniewaz wykryto naganny blad.");
                        sender.sendMessage(Color.RED + "Zglos to do niezwlocznie administracji serwera!");
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void registerCommand(Command command) {
        Validate.notNull(command, "command can not be null");
        if (this.registerServerCommand(command)) {
            this.commands.add(command);
        } else {
            Log.log(Level.WARNING, "Nieudana proba rejestracji komendy " + command.getCommands()[0]);
        }
    }
    
    public abstract boolean registerServerCommand(Command command);
    
    private void registerDefaultCommands() {
        this.registerCommand(new BeginCommand());
        this.registerCommand(new CancelCommand());
        this.registerCommand(new ChangelogCommand());
        this.registerCommand(new ClassCommand());
        this.registerCommand(new ClassesCommand());
        this.registerCommand(new CycleCommand());
        this.registerCommand(new EndCommand());
        this.registerCommand(new GlobalCommand());
        this.registerCommand(new JoinCommand());
        this.registerCommand(new LeaveCommand());
        this.registerCommand(new MapinfoCommand());
        this.registerCommand(new MaplistCommand());
        this.registerCommand(new MapnextCommand());
        this.registerCommand(new MatchinfoCommand());
        this.registerCommand(new MyteamCommand());
        this.registerCommand(new RateCommand());
        this.registerCommand(new RatingCommand());
        this.registerCommand(new RotationCommand());
        this.registerCommand(new SetnextCommand());
        this.registerCommand(new SpyCommand());
        this.registerCommand(new TeamsCommand());
        this.registerCommand(new TutorialCommand());
        this.registerCommand(new VariableCommand());
    }
}
