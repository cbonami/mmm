/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.handler.event.ClickEventSender;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the abstract base implementation of a {@link #click() clickable}
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetClickable<ADAPTER extends UiWidgetAdapterActive> extends
    AbstractUiWidgetActive<ADAPTER, Void> implements UiFeatureClick {

  /** @see #addClickHandler(UiHandlerEventClick) */
  private ClickEventSender clickEventSender;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetClickable(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.clickEventSender != null) {
      adapter.setClickEventSender(this, this.clickEventSender);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender == null) {
      this.clickEventSender = new ClickEventSender(this, getContext());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setClickEventSender(this, this.clickEventSender);
      }
    }
    this.clickEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender != null) {
      return this.clickEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    if (this.clickEventSender != null) {
      this.clickEventSender.onClick(this, true);
    }
  }

}
