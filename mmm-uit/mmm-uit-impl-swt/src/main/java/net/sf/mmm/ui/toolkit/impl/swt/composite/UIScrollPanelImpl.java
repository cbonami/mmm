/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncScrolledCompositeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface
 * using SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIScrollPanelImpl extends AbstractUIComposite implements UiScrollPanel {

  /** the synchron access to the scrolled composite */
  private final SyncScrolledCompositeAccess syncAccess;

  /** the child component */
  private AbstractUIComponent childComponent;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UIScrollPanelImpl(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject, null);
    int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
    this.syncAccess = new SyncScrolledCompositeAccess(uiFactory, style);
    this.childComponent = null;
  }

  /**
   * {@inheritDoc}
   */
  public void setComponent(UiElement child) {

    if (this.childComponent != null) {
      this.childComponent.setParent(null);
    }
    this.childComponent = (AbstractUIComponent) child;
    if (this.childComponent != null) {
      this.childComponent.setParent(this);
      // this.childComponent.getSwtControl().setParent(this.scrollPanel);
      Control childControl = this.childComponent.getSyncAccess().getSwtObject();
      if (childControl != null) {
        this.syncAccess.setContent(this.childComponent.getSyncAccess().getSwtObject());
        update();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create() {

    super.create();
    if (this.childComponent != null) {
      this.syncAccess.setContent(this.childComponent.getSyncAccess().getSwtObject());
      update();
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount() {

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  public UiElement getChild() {

    return this.childComponent;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent getChild(int index) {

    if (index == 0) {
      return this.childComponent;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * This method updates the required size.
   */
  @Override
  public void update() {

    if ((this.childComponent != null)
        && (this.childComponent.getSyncAccess().getSwtObject() != null)) {
      Point size = this.childComponent.getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT);
      this.syncAccess.setMinimumSize(size);
    }
    super.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncScrolledCompositeAccess getActiveSyncAccess() {

    return this.syncAccess;
  }
}
