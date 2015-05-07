/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.command.def;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.command.Command;
import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.scheduler.BeginScheduler;
import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BeginCommand extends Command {
    private final int maxSeconds = 120;
    private final int minSeconds = 5;
    
    public BeginCommand() {
        super(new String[] {"begin", "start"},
                "Rozpocznij mecz na obecnej mapie", "begin [seconds]");
        this.setPermission("arcade.command.begin");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            this.begin(sender, BeginScheduler.getDefaultSeconds());
        } else {
            int seconds = this.parseInteger(args[0], -1);
            if (seconds <= -1) {
                this.throwMessage(sender, CommandMessage.NUMBER_NEEDED);
            } else {
                this.begin(sender, seconds);
            }
        }
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void begin(Sender sender, int seconds) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNegative(seconds, "seconds can not be negative");
        if (Arcade.getMatches().getStatus() != MatchStatus.STARTING) {
            sender.sendError("Obecny tryb serwera nie jest startujacy!");
        } else if (seconds > this.maxSeconds) {
            sender.sendError("Przykro mi, lecz nie mozna ustawic tak dlugiego odliczania. Spróbuj go zmniejszyc.");
        } else if (seconds < this.minSeconds) {
            sender.sendError("Przykro mi, lecz nie mozna ustawic tak krótkiego odliczania. Spróbuj go zwiekszyc.");
        } else {
            CancelCommand.setDisabled(false);
            SchedulerManager schedulers = Arcade.getServer().getScheduler();
            schedulers.cancel();
            schedulers.runBegin(seconds);
        }
    }
}
