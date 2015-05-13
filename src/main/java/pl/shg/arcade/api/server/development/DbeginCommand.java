/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.development;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.command.def.CancelCommand;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.SchedulerManager;

/**
 *
 * @author Aleksander
 */
public class DbeginCommand extends Command {
    public DbeginCommand() {
        super(new String[] {"dbegin", "dstart"},
                Development.COMMAND_PREFIX + "Rozpocznij mecz na obecnej mapie", "dbegin");
        this.setPermission("arcade.command.begin");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (Arcade.getMatches().getStatus() != MatchStatus.STARTING) {
            sender.sendError("Obecny tryb serwera nie jest startujacy!");
        } else {
            CancelCommand.setDisabled(false);
            SchedulerManager manager = Arcade.getServer().getScheduler();
            manager.cancel();
            
            BeginScheduler begin = new BeginScheduler(0);
            try {
                Field endIgnored = begin.getClass().getDeclaredField("endIgnored");
                endIgnored.setAccessible(true);
                endIgnored.set(begin, Boolean.TRUE);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(DbeginCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int id = manager.runSync(begin);
            BeginScheduler.setID(id);
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
