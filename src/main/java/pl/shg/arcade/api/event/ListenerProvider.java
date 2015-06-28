/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang3.Validate;

/**
 * Represents a method that listen to an event.
 * @author Aleksander
 */
public final class ListenerProvider {
    private final Class<? extends Event> event;
    private final EventListener listener;
    private final Method method;
    private final Priority priority;
    
    public ListenerProvider(EventListener listener, Method method) {
        this.listener = listener;
        this.method = method;
        
        Annotation annotation = method.getAnnotation(EventSubscribtion.class);
        if (annotation == null) {
            throw new EventException("Event listener must be @EventSubscribtion decorated");
        }
        
        EventSubscribtion subscribtion = (EventSubscribtion) annotation;
        this.event = subscribtion.event();
        this.priority = subscribtion.priority();
    }
    
    public Class<? extends Event> getEvent() {
        return this.event;
    }
    
    public EventListener getListener() {
        return this.listener;
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public Priority getPriority() {
        return this.priority;
    }
    
    public static void fireEvent(EventListener inside, Method listener, Event event)
            throws EventException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Validate.notNull(inside);
        Validate.notNull(listener);
        Validate.notNull(event);
        
        listener.invoke(inside, event); // ugh..
    }
}
