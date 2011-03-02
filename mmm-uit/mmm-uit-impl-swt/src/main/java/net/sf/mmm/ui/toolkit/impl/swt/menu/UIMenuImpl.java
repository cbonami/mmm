/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenu;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncMenuAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenu} interface using SWT as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIMenuImpl extends AbstractUiMenu {

  /** the synchronous access to the menu */
  private final SyncMenuAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param swtMenu is the SWT menu to wrap.
   * @param text is the {@link MenuItem#setText(java.lang.String) text} of the
   *        menu.
   */
  public UIMenuImpl(UiFactorySwt uiFactory, Menu swtMenu, String text) {

    super(uiFactory);
    this.syncAccess = new SyncMenuAccess(uiFactory, SWT.CASCADE, swtMenu, text);
  }

  /**
   * This method gets synchron access on the SWT menu.
   * 
   * @return the synchron access.
   */
  public SyncMenuAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenuItem createMenuItem(String name, ButtonStyle style) {

    MenuItem menuItem = this.syncAccess.createMenuItem(name, style);
    return new UIMenuItemImpl((UiFactorySwt) getFactory(), this, name, style, menuItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenu createSubMenu(String name) {

    Menu subMenu = this.syncAccess.createSubMenu(name);
    return new UIMenuImpl((UiFactorySwt) getFactory(), subMenu, name);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void addSeparator() {

    this.syncAccess.addSeparator();
  }

}
