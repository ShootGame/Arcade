/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class Event {
    private static final HashMap<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();
    
    private final Class<? extends Event> event;
    
    public Event(Class<? extends Event> event) {
        Validate.notNull(event, "event can not be null");
        this.event = event;
    }
    
    public Class<? extends Event> getEventClass() {
        return this.event;
    }
    
    public String getEventName() {
        return this.getEventClass().getCanonicalName();
    }
    
    public static void callEvent(Event event) {
        Validate.notNull(event, "event can not be null");
        
        if (listeners.containsKey(event.getEventClass())) {
            callPriority(event, Priority.MONITOR); // this priority can't be canceled by the listener
            callPriority(event, Priority.HIGHEST);
            callPriority(event, Priority.HIGH);
            callPriority(event, Priority.NORMAL);
            callPriority(event, Priority.LOW);
            callPriority(event, Priority.LOWEST);
        }
    }
    
    public static EventExtra getExtra(EventListener listener) {
        Validate.notNull(listener, "listener can not be null");
        return listener.getClass().getDeclaredAnnotation(EventExtra.class);
    }
    
    public static Set<Class<? extends Event>> getRegisteredListeners() {
        return listeners.keySet();
    }
    
    public static boolean hasExtra(EventListener listener) {
        Validate.notNull(listener, "listener can not be null");
        return getExtra(listener) != null;
    }
    
    public static void registerListener(EventListener listener) {
        Validate.notNull(listener, "listener can not be null");
        if (!listeners.containsKey(listener.getEvent())) {
            listeners.put(listener.getEvent(), new ArrayList<EventListener>());
        }
        
        listeners.get(listener.getEvent()).add(listener);
    }
    
    public static void registerListener(EventListener... listeners) {
        Validate.notNull(listeners, "listeners can not be null");
        for (EventListener listener : listeners) {
            registerListener(listener);
        }
    }
    
    public static void unregisterListener(EventListener listener) {
        Validate.notNull(listener, "listener can not be null");
        for (Class<? extends Event> event : getRegisteredListeners()) {
            listeners.get(event).remove(listener);
        }
    }
    
    public static void unregisterListener(EventListener... listeners) {
        Validate.notNull(listeners, "listeners can not be null");
        for (EventListener listener : listeners) {
            unregisterListener(listener);
        }
    }
    
    private static void callPriority(Event event, Priority priority) {
        for (EventListener listener : listeners.get(event.getEventClass())) {
            EventExtra extra = getExtra(listener);
            Priority listenerPriority = Priority.NORMAL;
            if (extra != null) {
                listenerPriority = extra.priority();
            }
            
            if (listenerPriority == priority) {
                listener.handle(event);
                
                if (event instanceof CancelableEvent && priority.isCancelable() && ((CancelableEvent) event).isCancel()) {
                    break;
                }
            }
        }
    }
}
