/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.menu.AbstractUiWidgetMenu;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.menu.adapter.UiWidgetAdapterGwtMenu;

/**
 * This is the implementation of {@link UiWidgetMenu} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuGwt extends AbstractUiWidgetMenu<UiWidgetAdapterGwtMenu> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetMenuGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenu createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenu();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetMenu> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenu.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenu create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetMenuGwt(factory);
    }

  }

}