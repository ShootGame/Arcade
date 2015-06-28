/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.command.CommandException;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.development.TestCommand;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class EventTest extends TestCommand.Test {
    public EventTest() {
        super("event", null, true, "dzialanie i priorytety zdarzen");
    }
    
    @Override
    public void execute(Sender sender, String[] args) throws CommandException {
        sender.sendSuccess("Rejestracja sluchaczy zdarzen...");
        Event.registerListener(new Listeners(sender));
        
        sender.sendSuccess("Wywolywanie zdarzenia...");
        Event.callEvent(new SampleEvent());
    }
    
    private class Listeners implements EventListener {
        private final Sender sender;
        
        public Listeners(Sender sender) {
            this.sender = sender;
        }
        
        public void handle(Event e, Priority priority) {
            this.sender.sendMessage(Color.AQUA + "Wywolywanie " + e.getEventName() + " - " + priority.toString());
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.MONITOR)
        public void handleMonitor(Event e) {
            this.handle(e, Priority.MONITOR);
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.HIGHEST)
        public void handleHighest(Event e) {
            this.handle(e, Priority.HIGHEST);
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.HIGH)
        public void handleHigh(Event e) {
            this.handle(e, Priority.HIGH);
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.NORMAL)
        public void handleNormal(Event e) {
            this.handle(e, Priority.NORMAL);
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.LOW)
        public void handleLow(Event e) {
            this.handle(e, Priority.LOW);
        }
        
        @EventSubscribtion(event = SampleEvent.class, priority = Priority.LOWEST)
        public void handleLowest(Event e) {
            this.handle(e, Priority.LOWEST);
        }
    }
    
    private class SampleEvent extends Event {
        public SampleEvent() {
            super(SampleEvent.class);
        }
    }
}
