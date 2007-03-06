/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event;

/**
 * This is the interface for a generic event listener.
 * 
 * @param <E>
 *        is the templated type of events to listen to.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface EventListener<E extends Event> extends java.util.EventListener {

  /**
   * This method is called if an event occurred.<br>
   * <b>WARNING:</b><br>
   * It is NOT legal to {@link EventSource#addListener(EventListener) add} or
   * {@link EventSource#removeListener(EventListener) remove} listeners during
   * the call of this method. Depending on the implementation of
   * {@link EventSource} this may cause dead-locks.
   * 
   * @param event
   *        is the event that notifies about something that happened.
   */
  void handleEvent(E event);

}
