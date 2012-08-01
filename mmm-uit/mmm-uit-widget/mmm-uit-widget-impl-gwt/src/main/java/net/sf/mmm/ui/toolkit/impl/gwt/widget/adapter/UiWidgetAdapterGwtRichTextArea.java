/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterRichTextArea;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtRichTextArea.MyRichTextArea;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * This is the implementation of {@link UiWidgetAdapterRichTextArea} using GWT based on {@link RichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link String}.
 */
public class UiWidgetAdapterGwtRichTextArea<VALUE> extends UiWidgetAdapterGwtTextAreaBase<MyRichTextArea, VALUE>
    implements UiWidgetAdapterRichTextArea<MyRichTextArea, VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtRichTextArea() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MyRichTextArea createWidget() {

    return new MyRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInTextLines() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInTextLines(int lines) {

    getWidget().setHeight(lines + "em");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getWidget().getElement().setAttribute("maxlength", Integer.toString(length));
  }

  /**
   * This inner class makes {@link RichTextArea} implement {@link HasValue}.
   */
  public static class MyRichTextArea extends RichTextArea implements HasValue<String>, HasChangeHandlers {

    /**
     * The constructor.
     */
    public MyRichTextArea() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addChangeHandler(ChangeHandler handler) {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return getHTML();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {

      setHTML(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value, boolean fireEvents) {

      setHTML(value);
    }

  }

}
