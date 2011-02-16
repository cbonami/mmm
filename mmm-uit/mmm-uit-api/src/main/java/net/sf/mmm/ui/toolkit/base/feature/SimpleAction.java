/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.Action} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SimpleAction extends AbstractAction {

  /** the {@link #getActionListener() listener} */
  private UIActionListener listener;

  /**
   * The constructor.
   * 
   * @param actionListener is the {@link #getActionListener() listener}.
   * @param displayName is the {@link #getName() name}.
   */
  public SimpleAction(UIActionListener actionListener, String displayName) {

    this(actionListener, displayName, null);
  }

  /**
   * The constructor.
   * 
   * @param actionListener is the {@link #getActionListener() listener}.
   * @param displayName is the {@link #getName() name}.
   * @param displayIcon is the {@link #getIcon() icon}.
   */
  public SimpleAction(UIActionListener actionListener, String displayName, UiImage displayIcon) {

    super(displayName);
    this.listener = actionListener;
  }

  /**
   * This method sets the {@link #getActionListener() listener}.
   * 
   * @param newListener is the new listener to set.
   */
  public void setActionListener(UIActionListener newListener) {

    this.listener = newListener;
  }

  /**
   * {@inheritDoc}
   */
  public UIActionListener getActionListener() {

    return this.listener;
  }

}
