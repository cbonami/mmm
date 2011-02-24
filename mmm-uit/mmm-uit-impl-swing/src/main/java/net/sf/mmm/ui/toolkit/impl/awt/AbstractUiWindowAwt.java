/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.common.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.window.AbstractUiWindow;

/**
 * This class is the implementation of the UIWindow interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWindowAwt extends AbstractUiWindow {

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiNode#getParent() parent} that
   *        created this object. It may be <code>null</code>.
   */
  public AbstractUiWindowAwt(AbstractUiFactory uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * This method gets the unwrapped window.
   * 
   * @return the unwrapped window object.
   */
  protected abstract Window getAwtWindow();

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    WindowListener listener = new WindowListener() {

      /**
       * {@inheritDoc}
       */
      public void windowActivated(WindowEvent e) {

        invoke(ActionType.ACTIVATE);
      }

      /**
       * {@inheritDoc}
       */
      public void windowClosed(WindowEvent e) {

        invoke(ActionType.HIDE);
      }

      /**
       * {@inheritDoc}
       */
      public void windowClosing(WindowEvent e) {

      // currently ignored...
      }

      /**
       * {@inheritDoc}
       */
      public void windowDeactivated(WindowEvent e) {

        invoke(ActionType.DEACTIVATE);
      }

      /**
       * {@inheritDoc}
       */
      public void windowDeiconified(WindowEvent e) {

        invoke(ActionType.DEICONIFY);
      }

      /**
       * {@inheritDoc}
       */
      public void windowIconified(WindowEvent e) {

        invoke(ActionType.DEACTIVATE);
      }

      /**
       * {@inheritDoc}
       */
      public void windowOpened(WindowEvent e) {

        invoke(ActionType.SHOW);
      }

    };
    getAwtWindow().addWindowListener(listener);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isVisible() {

    return getAwtWindow().isVisible();
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visible) {

    getAwtWindow().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    getAwtWindow().pack();
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    getAwtWindow().setLocation(x, y);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    getAwtWindow().setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return getAwtWindow().getX();
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return getAwtWindow().getY();
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getAwtWindow().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getAwtWindow().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    super.dispose();
    getAwtWindow().dispose();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    // TODO this is not correct in all situations, is it?
    return getAwtWindow().isDisplayable();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizeable() {

    if (getType() == UiDialog.TYPE) {
      return ((Dialog) getAwtWindow()).isResizable();
    } else {
      return ((Frame) getAwtWindow()).isResizable();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    ScriptOrientation orientation = getFactory().getScriptOrientation();
    if (orientation.isLeftToRight()) {
      getAwtWindow().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    } else {
      getAwtWindow().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
  }

}
