/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.adapter;

import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.panel.adapter.UiWidgetAdapterPanel} using
 * GWT based on {@link VerticalPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtVerticalPanel extends UiWidgetAdapterGwtPanel<VerticalPanel> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtVerticalPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VerticalPanel createWidget() {

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setWidth("100%");
    return verticalPanel;
  }

}
