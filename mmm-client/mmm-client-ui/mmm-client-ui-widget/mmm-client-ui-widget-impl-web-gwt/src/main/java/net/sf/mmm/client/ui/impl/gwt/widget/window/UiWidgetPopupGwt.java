/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetPopup;
import net.sf.mmm.client.ui.impl.gwt.widget.window.adapter.UiWidgetAdapterGwtPopup;

/**
 * This is the implementation of {@link UiWidgetPopup} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPopupGwt extends AbstractUiWidgetPopup<UiWidgetAdapterGwtPopup> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetPopupGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtPopup createWidgetAdapter() {

    return new UiWidgetAdapterGwtPopup();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetPopup> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetPopup.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetPopup create(UiContext context) {

      return new UiWidgetPopupGwt(context);
    }
  }

}
