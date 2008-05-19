/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import net.sf.mmm.util.event.api.ChangeEvent;
import net.sf.mmm.util.event.api.ChangeEventType;

/**
 * This is the abstract base class of all events sent from models of the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIModelEvent implements ChangeEvent {

  /** the event type */
  private ChangeEventType type;

  /**
   * The constructor.
   * 
   * @param eventType is the type for the new event.
   */
  protected UIModelEvent(ChangeEventType eventType) {

    super();
    this.type = eventType;
  }

  /**
   * {@inheritDoc}
   */
  public ChangeEventType getType() {

    return this.type;
  }

}
