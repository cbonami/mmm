/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadSize;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiComposite;

/**
 * This is the base implementation of the UIWindow interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiWindow<DELEGATE> extends AbstractUiNode<DELEGATE> implements UiWindow {

  /** the composite content of this window */
  private AbstractUiComposite<?, ? extends UiElement> composite;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWindow(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract UiWindowAdapter<DELEGATE> getAdapter();

  /**
   * This method gets access to read the size of the desktop.<br>
   * It is used by {@link #centerWindow()} and may be overridden in specific
   * scenarios.
   * 
   * @return read-access to the size of the desktop.
   */
  protected AttributeReadSize getDesktopSize() {

    return getFactory().getDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void centerWindow() {

    AttributeReadSize desktop = getDesktopSize();
    int xDiff = desktop.getWidth() - getWidth();
    int yDiff = desktop.getHeight() - getHeight();
    if (xDiff < 0) {
      xDiff = 0;
    }
    if (yDiff < 0) {
      yDiff = 0;
    }
    setPosition(xDiff / 2, yDiff / 2);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUiComposite<?, ? extends UiElement> getComposite() {

    return this.composite;
  }

  /**
   * This method registers the new composite and changes the parent of the old
   * and the new composite. The method should be called from the
   * {@link #setComposite(UiComposite)} method implementation.
   * 
   * @see UiWindow#setComposite(UiComposite)
   * 
   * @param newComposite is the composite to register.
   */
  @SuppressWarnings("unchecked")
  protected void doSetComposite(UiComposite<? extends UiElement> newComposite) {

    if (this.composite != null) {
      // The current composite is replaced by a new one. Set the parent of
      // the old (current) one to null.
      this.composite.setParent(null);
    }
    this.composite = ((AbstractUiComposite<?, ? extends UiElement>) newComposite);
    this.composite.setParent(this);
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    getAdapter().setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    getAdapter().setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return getAdapter().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    getAdapter().setPosition(x, y);
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getAdapter().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getAdapter().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return getAdapter().getTitle();
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return getAdapter().getX();
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return getAdapter().getY();
  }

  /**
   * {@inheritDoc}
   */
  public UiDialog createDialog(String title, boolean modal, boolean resizeable) {

    return getFactory().createDialog(this, title, modal, resizeable);
  }

  /**
   * {@inheritDoc}
   */
  public void showMessage(String message, String title, MessageType messageType) {

    getFactory().showMessage(this, message, title, messageType, null);
  }

  /**
   * {@inheritDoc}
   */
  public void showMessage(String message, String title, MessageType messageType, Throwable throwable) {

    getFactory().showMessage(this, message, title, messageType, throwable);
  }

  /**
   * {@inheritDoc}
   */
  public boolean showQuestion(String question, String title) {

    return getFactory().showQuestion(this, question, title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (this.composite != null) {
      this.composite.refresh(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    super.dispose();
    getFactory().removeWindow(this);
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    getAdapter().pack();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWindow getParent() {

    return (UiWindow) super.getParent();
  }

}
