/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tournament;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.match.MatchEndedEvent;
import pl.shg.arcade.api.scheduler.CycleScheduler;

/**
 *
 * @author Aleksander
 */
public class CancelCycleListener implements EventListener {
    @EventSubscribtion(event = MatchEndedEvent.class)
    public void handleMatchEnded(MatchEndedEvent event) {
        Arcade.getServer().getScheduler().cancel(CycleScheduler.getID());
    }
}
