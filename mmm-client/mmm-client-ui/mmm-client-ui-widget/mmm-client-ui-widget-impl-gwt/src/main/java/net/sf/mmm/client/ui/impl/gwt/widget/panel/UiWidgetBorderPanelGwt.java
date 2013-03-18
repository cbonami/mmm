/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetBorderPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtBorderPanel;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetBorderPanelGwt extends AbstractUiWidgetBorderPanel<UiWidgetAdapterGwtBorderPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetBorderPanelGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtBorderPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtBorderPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetBorderPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetBorderPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetBorderPanel create(AbstractUiContext context) {

      return new UiWidgetBorderPanelGwt(context);
    }
  }

}
