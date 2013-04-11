/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetRichTextArea;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtRichTextArea;

/**
 * This is the implementation of {@link UiWidgetRichTextArea} using GWT based on
 * {@link UiWidgetAdapterGwtRichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRichTextAreaGwt extends AbstractUiWidgetRichTextArea<UiWidgetAdapterGwtRichTextArea<String>> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRichTextAreaGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRichTextArea<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtRichTextArea<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetRichTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRichTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRichTextArea create(UiContext context) {

      return new UiWidgetRichTextAreaGwt(context);
    }

  }

}
