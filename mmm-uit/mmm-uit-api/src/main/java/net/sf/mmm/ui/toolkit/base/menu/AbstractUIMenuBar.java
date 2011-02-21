/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.menu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUINode;

/**
 * This is the base implementation of the UIMenuBar interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIMenuBar extends AbstractUINode implements UiMenuBar {

  /** maps the name of a menu (String) to a menu (UIMenu) */
  private Map<String, UiMenu> menuTable;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactory instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIMenuBar(AbstractUiFactory uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.menuTable = new HashMap<String, UiMenu>();
  }

  /**
   * {@inheritDoc}
   */
  public UiMenu addMenu(String name) {

    synchronized (this.menuTable) {
      UiMenu menu = this.menuTable.get(name);
      if (menu == null) {
        // the menu does not already exist and is created here...
        menu = createMenu(name);
        this.menuTable.put(name, menu);
      }
      return this.menuTable.get(name);
    }
  }

  /**
   * This method creates a menu in this menu bar.
   * 
   * @see net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar#addMenu(java.lang.String)
   * 
   * @param name is the name of the menu.
   * @return the created menu.
   */
  protected abstract UiMenu createMenu(String name);

  /**
   * {@inheritDoc}
   */
  public UiMenu getMenu(String name) {

    return this.menuTable.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public int getMenuCount() {

    return this.menuTable.size();
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<UiMenu> getMenus() {

    return this.menuTable.values().iterator();
  }

}
