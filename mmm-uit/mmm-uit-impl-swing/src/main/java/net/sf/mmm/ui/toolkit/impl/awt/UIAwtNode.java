/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUiNode;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiNode} interface for AWT/Swing
 * implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UIAwtNode extends AbstractUiNode {

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UIAwtNode(AbstractUiFactory uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * This method creates a default AWT action-listener that adapts the events.
   * 
   * @return the new listener.
   */
  protected ActionListener createActionListener() {

    ActionListener listener = new ActionListener() {

      /**
       * {@inheritDoc}
       */
      public void actionPerformed(ActionEvent e) {

        invoke(ActionType.SELECT);
      }

    };
    return listener;
  }

  /**
   * This method creates a default AWT change-listener that adapts the events.
   * 
   * @return the new listener.
   */
  protected ChangeListener createChangeListener() {

    ChangeListener listener = new ChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void stateChanged(ChangeEvent e) {

        invoke(ActionType.SELECT);
      }

    };
    return listener;
  }

}
