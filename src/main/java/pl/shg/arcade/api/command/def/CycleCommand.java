/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.scheduler.CycleScheduler;
import pl.shg.arcade.api.scheduler.SchedulerManager;

/**
 *
 * @author Aleksander
 */
public class CycleCommand extends Command {
    private final int maxSeconds = 120;
    private final int minSeconds = 5;
    
    public CycleCommand() {
        super(new String[] {"cycle"},
                "Zaladuj nastepna mape", "cycle [seconds]");
        this.setPermission("arcade.command.cycle");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            this.cycle(sender, CycleScheduler.getDefaultSeconds());
        } else {
            int seconds = this.parseInteger(args[0], -1);
            if (seconds <= -1) {
                this.throwMessage(sender, CommandMessage.NUMBER_NEEDED);
            } else {
                this.cycle(sender, seconds);
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void cycle(Sender sender, int seconds) {
        Validate.notNull(sender, "sender can not be null");
        Validate.isTrue(seconds >= 0);
        MatchStatus status = Arcade.getMatches().getStatus();
        if (status == MatchStatus.PLAYING) {
            sender.sendError("Obecny tryb gry nie jest startujacy, ani konczacy!");
        } else if (seconds > this.maxSeconds) {
            sender.sendError("Przykro mi, lecz nie mozna ustawic tak dlugiego odliczania. Spróbuj go zmniejszyc.");
        } else if (seconds < this.minSeconds) {
            sender.sendError("Przykro mi, lecz nie mozna ustawic tak krótkiego odliczania. Spróbuj go zwiekszyc.");
        } else {
            CancelCommand.setDisabled(false);
            SchedulerManager schedulers = Arcade.getServer().getScheduler();
            schedulers.cancel();
            schedulers.runCycle(seconds);
        }
    }
}
