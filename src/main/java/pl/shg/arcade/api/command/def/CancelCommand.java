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
import pl.shg.arcade.api.scheduler.SchedulerManager;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class CancelCommand extends Command {
    private static boolean disabled = false;
    
    public CancelCommand() {
        super(new String[] {"cancel"},
                "Zatrzymaj i anuluj obecne odliczanie", "cancel [-h]");
        this.setOption("-h", "wlacz/wylacz automatyczne odliczanie");
        this.setPermission("arcade.command.cancel");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        this.cancel(sender, Arcade.getMatches().getStatus(), this.hasFlag(args, 'h'));
    }
    
    @Override
    public int minArguments() {
        return 0;
    }
    
    private void cancel(Sender sender, ArcadeMatchStatus status, boolean hard) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(status, "status can not be null");
        if (hard) {
            setDisabled(!isDisabled());
            if (isDisabled()) {
                sender.sendSuccess("Wylaczono automatyczne odliczanie.");
            } else {
                sender.sendSuccess("Wlaczono automatyczne odliczanie.");
            }
        } else if (status == ArcadeMatchStatus.STARTING || status == ArcadeMatchStatus.CYCLING) {
            SchedulerManager schedulers = Arcade.getServer().getScheduler();
            int amount = schedulers.getIDs().size();
            if (amount != 0) {
                schedulers.cancel();
                sender.sendSuccess("Zatrzymano wszystkie odlicznia (" + amount + ").");
            } else {
                sender.sendError("Obecnie nie trwa zadne odliczanie");
            }
        } else {
            sender.sendError("Obecny tryb gry nie jest startujacy, ani konczacy!");
        }
    }
    
    public static boolean isDisabled() {
        return disabled;
    }
    
    public static void setDisabled(boolean disabled) {
        CancelCommand.disabled = disabled;
    }
}
