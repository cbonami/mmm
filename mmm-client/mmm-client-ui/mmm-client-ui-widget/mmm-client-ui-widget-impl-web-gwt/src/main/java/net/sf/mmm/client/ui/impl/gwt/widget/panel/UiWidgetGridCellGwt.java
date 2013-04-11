/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetGridCell;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridCell;

/**
 * This is the implementation of {@link UiWidgetGridCell} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetGridCellGwt extends AbstractUiWidgetGridCell<UiWidgetAdapterGwtGridCell> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetGridCellGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtGridCell createWidgetAdapter() {

    return new UiWidgetAdapterGwtGridCell();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetGridCell> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetGridCell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetGridCell create(UiContext context) {

      return new UiWidgetGridCellGwt(context);
    }
  }

}
