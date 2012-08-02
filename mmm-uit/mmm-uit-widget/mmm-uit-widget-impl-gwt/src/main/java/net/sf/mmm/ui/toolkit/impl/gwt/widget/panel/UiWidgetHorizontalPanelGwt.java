/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtHorizontalPanel;

/**
 * This is the implementation of {@link UiWidgetHorizontalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetHorizontalPanelGwt extends
    AbstractUiWidgetDynamicComposite<UiWidgetAdapterGwtHorizontalPanel, UiWidgetRegular> implements
    UiWidgetHorizontalPanel {

  /**
   * The constructor.
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetHorizontalPanelGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtHorizontalPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtHorizontalPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetHorizontalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetHorizontalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetHorizontalPanel create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetHorizontalPanelGwt(factory);
    }
  }

}
