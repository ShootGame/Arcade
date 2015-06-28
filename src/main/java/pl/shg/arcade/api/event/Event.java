/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class Event {
    private static CallListener callListener;
    private static final HashMap<Class<? extends Event>, List<ListenerProvider>> listeners = new HashMap<>();
    
    private final Class<? extends Event> event;
    
    public Event(Class<? extends Event> event) {
        Validate.notNull(event);
        this.event = event;
    }
    
    public Class<? extends Event> getEventClass() {
        return this.event;
    }
    
    public String getEventName() {
        return this.getEventClass().getSimpleName();
    }
    
    public static void callEvent(Event event) {
        Validate.notNull(event);
        
        if (getListener() != null) {
            getListener().call(event);
        }
        
        if (!listeners.containsKey(event.getEventClass())) {
            // this event doesn't have any listeners
            return;
        }
        
        for (Priority priority : Priority.values()) {
            callPriority(event, priority);
        }
    }
    
    public static Collection<List<ListenerProvider>> getAllProviders() {
        return listeners.values();
    }
    
    public static CallListener getListener() {
        return callListener;
    }
    
    public static List<ListenerProvider> getProviders(Class<? extends Event> event) {
        Validate.notNull(event);
        return listeners.get(event);
    }
    
    public static Set<Class<? extends Event>> getRegisteredListeners() {
        return listeners.keySet();
    }
    
    public static void registerListener(EventListener... listeners) {
        Validate.notNull(listeners);
        
        for (EventListener listener : listeners) {
            registerListenerClass(listener);
        }
    }
    
    public static void setListener(CallListener listener) {
        callListener = listener;
    }
    
    public static void unregisterListener(EventListener... listeners) {
        Validate.notNull(listeners);
        
        for (EventListener listener : listeners) {
            unregisterListenerClass(listener);
        }
    }
    
    private static void callPriority(Event event, Priority priority) {
        for (ListenerProvider provider : listeners.get(event.getClass())) {
            if (!priority.equals(provider.getPriority())) {
                continue;
            }
            
            try {
                ListenerProvider.fireEvent(provider.getListener(), provider.getMethod(), event); // call the event
            } catch (EventException ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, "Could not handle " + event.getEventName() +
                        " in " + provider.getEvent().getSimpleName() + " because of listener", ex);
            } catch (Throwable ex) {
                Logger.getLogger(Event.class.getName()).log(Level.SEVERE, "Could not handle " + event.getEventName() +
                        " in " + provider.getEvent().getSimpleName() + " - " + ex.getMessage(), ex);
            }
            
            if (event instanceof CancelableEvent && priority.isCancelable() && ((CancelableEvent) event).isCancel()) {
                break;
            }
        }
    }
    
    private static void fireRegistration(ListenerProvider provider) {
        Class<? extends Event> eventClass = provider.getEvent();
        if (!listeners.containsKey(eventClass)) {
            listeners.put(eventClass, new ArrayList<>());
        }
        
        listeners.get(eventClass).add(provider);
    }
    
    private static void registerListenerClass(EventListener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (Modifier.isAbstract(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            method.setAccessible(true);
            
            Annotation annotation = method.getDeclaredAnnotation(EventSubscribtion.class);
            if (annotation == null) {
                continue;
            }
            
            fireRegistration(new ListenerProvider(listener, method));
        }
    }
    
    private static void unregisterListenerClass(EventListener listener) {
        Iterator<List<ListenerProvider>> it = new ArrayList<>(getAllProviders()).iterator();
        
        while (it.hasNext()) {
            for (ListenerProvider provider : it.next()) {
                if (provider.getListener().equals(listener)) {
                    listeners.get(provider.getEvent()).remove(provider);
                }
            }
        }
    }
}
