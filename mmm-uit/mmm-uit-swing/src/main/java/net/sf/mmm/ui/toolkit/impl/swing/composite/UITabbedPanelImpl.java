/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITabbedPanelImpl extends UIMultiComposite implements UITabbedPanel {

  /** the native swing component */
  private final JTabbedPane panel;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITabbedPanelImpl(UIFactorySwing uiFactory, AbstractUIComponent parentObject) {

    super(uiFactory, parentObject);
    this.panel = new JTabbedPane();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.panel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String)
   */
  public void addComponent(UIComponent component, String title) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    this.panel.add(title, c.getSwingComponent());
    setParent(c, this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String, int)
   */
  public void addComponent(UIComponent component, String title, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    this.panel.insertTab(title, null, c.getSwingComponent(), null, position);
    setParent(c, this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void removeComponent(UIComponent component) {

    Component c = ((AbstractUIComponent) component).getSwingComponent();
    int position = this.panel.indexOfComponent(c);
    if (position >= 0) {
      removeComponent(position);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#removeComponent(int)
   */
  public void removeComponent(int position) {

    this.panel.remove(position);
    this.components.remove(position);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

}
