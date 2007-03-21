/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;

/**
 * This class is an abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite} interface using SWT
 * as the UI toolkit. It is used for composites that have a list of multiple
 * child-components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIMultiComposite extends AbstractUIComposite {

  /** the component list */
  protected final List<AbstractUIComponent> components;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param borderTitle
   *        is the title of the border or <code>null</code> for NO border.
   */
  public UIMultiComposite(UIFactorySwt uiFactory, UISwtNode parentObject, String borderTitle) {

    super(uiFactory, parentObject, borderTitle);
    this.components = new ArrayList<AbstractUIComponent>();
  }

  /**
   * {@inheritDoc}
   */
  public int getComponentCount() {

    return this.components.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUIComponent getComponent(int index) {

    return this.components.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UINode newParent) {

    super.setParent(newParent);
  }

}
