/* $Id$ */
package net.sf.mmm.event;

import java.util.EventListener;

/**
 * This is the interface for a generic event listener.
 * 
 * @param <E>
 *        is the templated type of events to listen to.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface EventListenerIF<E extends EventIF> extends EventListener {

    /**
     * This method is called if an event occured.
     * 
     * @param event
     *        is the event that notifies about something that happened.
     */
    void handleEvent(E event);

}