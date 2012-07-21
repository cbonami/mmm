/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRadioButtons;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This is the implementation of {@link UiWidgetRadioButtons} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetRadioButtonsGwt<VALUE> extends UiWidgetRadioButtonsGwtBase<VALUE, HorizontalPanel> implements
    UiWidgetRadioButtons<VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetRadioButtonsGwt() {

    super(new HorizontalPanel());
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetRadioButtons> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtons.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetRadioButtons create() {

      return new UiWidgetRadioButtonsGwt();
    }

  }

}
