/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.common.UiEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.dynamic.VisibilityFlagModifier;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetClickable;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;

/**
 * This is the abstract base implementation of {@link UiWidgetCollapsableSection}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetCollapsableSection<ADAPTER extends UiWidgetAdapterCollapsableSection> extends
    AbstractUiWidgetClickable<ADAPTER> implements UiWidgetCollapsableSection {

  /** @see #isCollapsed() */
  private boolean collapsed;

  /** @see #addCollapseWidget(UiWidget) */
  private CollapseHandler collapseHandler;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetCollapsableSection(UiContext context) {

    super(context);
    setPrimaryStyle(PRIMARY_STYLE);
    this.collapsed = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.collapsed) {
      adapter.setCollapsed(this.collapsed);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().isCollapsed();
    }
    return this.collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setCollapsed(collapsed);
    } else {
      if (this.collapsed == collapsed) {
        return;
      }
      UiEvent event = EventType.EXPAND;
      if (collapsed) {
        event = EventType.COLLAPSE;
      }
      fireEvent(event, true);
    }
    this.collapsed = collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCollapseHandler(UiHandlerEventCollapse handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeCollapseHandle(UiHandlerEventCollapse handler) {

    return removeEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCollapseWidget(UiWidget widget) {

    if (this.collapseHandler == null) {
      this.collapseHandler = new CollapseHandler();
      addCollapseHandler(this.collapseHandler);
    }
    this.collapseHandler.widgets.add(widget);
  }

  /**
   * @see AbstractUiWidgetCollapsableSection#addCollapseWidget(UiWidget)
   */
  private class CollapseHandler extends UiHandlerEventCollapse {

    /** The list of {@link UiWidget} to collapse and expand. */
    private final List<UiWidget> widgets;

    /**
     * The constructor.
     */
    public CollapseHandler() {

      super();
      this.widgets = new LinkedList<UiWidget>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollapseOrExpand(UiFeatureCollapse source, boolean collapse, boolean programmatic) {

      for (UiWidget widget : this.widgets) {
        widget.getVisibleFlag().setFlag(!collapse, VisibilityFlagModifier.MODIFIER_COLLAPSE);
        if (widget instanceof UiWidgetField) {
          UiWidgetField<?> field = (UiWidgetField<?>) widget;
          field.getFieldLabelWidget().getVisibleFlag().setFlag(!collapse, VisibilityFlagModifier.MODIFIER_COLLAPSE);
        }
      }
    }

  }

}