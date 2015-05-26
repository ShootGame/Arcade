/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.development;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.command.def.CancelCommand;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.scheduler.SchedulerManager;

/**
 *
 * @author Aleksander
 */
public class DcycleCommand extends Command {
    public DcycleCommand() {
        super(new String[] {"dcycle"},
                Development.COMMAND_PREFIX + "Zaladuj nastepna mape", "dcycle [map...]");
        this.setOption("-r", "restartuj serwer");
        this.setPermission("arcade.command.cycle");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
            sender.sendError("Obecny tryb gry nie jest startujacy, ani konczacy!");
            return;
        } else if (args.length > 0) {
            Arcade.getCommands().perform("setnext", sender, args);
        }
        CancelCommand.setDisabled(false);
        SchedulerManager manager = Arcade.getServer().getScheduler();
        manager.cancel();
        
        Map nextMap = Arcade.getMaps().getNextMap();
        if (nextMap != null) {
            manager.runCycle(0);
        } else {
            Arcade.getServer().shutdown();
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
}
