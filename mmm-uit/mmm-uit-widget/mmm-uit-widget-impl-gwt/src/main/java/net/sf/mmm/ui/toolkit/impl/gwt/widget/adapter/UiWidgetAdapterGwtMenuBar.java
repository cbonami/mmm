/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterDynamicComposite;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterDynamicComposite} using GWT based on
 * {@link MenuBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenuBar extends UiWidgetAdapterGwtWidget<MenuBar> implements
    UiWidgetAdapterDynamicComposite<MenuBar, UiWidgetMenu> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenuBar() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuBar createWidget() {

    return new MenuBar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetMenu child, int index) {

    MenuItem menu = getWidget(child, MenuItem.class);
    getWidget().addItem(menu);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetMenu child, int index) {

    MenuItem menu = getWidget(child, MenuItem.class);
    getWidget().removeItem(menu);
  }

}
