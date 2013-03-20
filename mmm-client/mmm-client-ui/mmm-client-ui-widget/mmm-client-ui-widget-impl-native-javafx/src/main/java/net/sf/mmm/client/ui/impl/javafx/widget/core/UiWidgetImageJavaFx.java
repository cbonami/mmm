/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.core;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetImage;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.impl.javafx.widget.core.adapter.UiWidgetAdapterJavaFxImage;

/**
 * This is the implementation of {@link UiWidgetImage} using JavaFx.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetImageJavaFx extends AbstractUiWidgetImage<UiWidgetAdapterJavaFxImage> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetImageJavaFx(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterJavaFxImage createWidgetAdapter() {

    return new UiWidgetAdapterJavaFxImage();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetImage> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetImage create(AbstractUiContext context) {

      return new UiWidgetImageJavaFx(context);
    }

  }

}
