/* $Id: AbstractUIComposite.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.util.nls.api.IllegalCaseException;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} interface using
 * SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiComposite<E extends AbstractUiElement> extends AbstractUiElement
    implements UiComposite<E> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiComposite(UiFactorySwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractSyncCompositeAccess getSyncAccess() {

    return getActiveSyncAccess();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract AbstractSyncCompositeAccess getActiveSyncAccess();

  /**
   * {@inheritDoc}
   */
  @Override
  public void create() {

    super.create();
    createChildren();
  }

  /**
   * This method is called to create the children of this composite.
   */
  protected void createChildren() {

    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      AbstractUiElement child = getChild(i);
      if (child != null) {
        child.create();
      }
    }
  }

  /**
   * This method converts the given {@link Orientation} to the according
   * {@link SWT} magic constant.
   * 
   * @param orientation is the {@link Orientation}.
   * @return the {@link SWT} constant.
   */
  protected int convertOrientation(Orientation orientation) {

    switch (orientation) {
      case HORIZONTAL:
        return SWT.HORIZONTAL;
      case VERTICAL:
        return SWT.VERTICAL;
      default :
        throw new IllegalCaseException(Orientation.class, orientation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // TODO
    super.dispose();
  }

  /**
   * This method determines if an SWT child will automatically be attached to
   * this {@link #getActiveSyncAccess() composite}. Override this method and
   * return <code>false</code> if you need special behavior to build the SWT
   * tree.
   * 
   * @return <code>true</code> if children should be attached automatically,
   *         <code>false</code> if your implementation needs special behavior.
   */
  public boolean isAttachToActiveAccess() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    int componentCount = getChildCount();
    for (int i = 0; i < componentCount; i++) {
      AbstractUiElement component = getChild(i);
      component.refresh(event);
    }
  }

}
