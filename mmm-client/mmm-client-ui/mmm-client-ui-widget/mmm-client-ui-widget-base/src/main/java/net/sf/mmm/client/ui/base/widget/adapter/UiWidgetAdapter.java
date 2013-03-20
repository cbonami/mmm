/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteAttribute;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSize;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStyles;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetLabel;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;

/**
 * This is the interface that adapts to the native {@link #getToplevelWidget() widget} of the underlying
 * toolkit implementation for a {@link net.sf.mmm.client.ui.base.widget.AbstractUiWidgetReal}.<br/>
 * It is a design trade-off as java does not have multi-inheritance (we would need scala traits here). This
 * way it is possible to implement an abstract base-implementation for the types of the
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}-hierarchy and inherit different implementations (Swing,
 * SWT, GWT, etc.) from that without creating redundant code.<br/>
 * <b>ATTENTION:</b><br/>
 * The getters (<code>AttributeRead*</code> methods) of attributes that cannot be changed by the end-user are
 * never used and therefore NOT implemented properly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapter extends AttributeWriteHtmlId, AttributeWriteTooltip, AttributeWriteVisible,
    AttributeWriteEnabled, AttributeWriteStyles, AttributeWriteDisposed, AttributeWriteSize, AttributeWriteAttribute {

  /**
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetFactory#getNativeWidget(net.sf.mmm.client.ui.api.widget.UiWidgetRegular)
   * 
   * @return the native widget.
   */
  Object getToplevelWidget();

  /**
   * This method removes the {@link #getToplevelWidget() widget} from its parent. After this method, the
   * {@link #getToplevelWidget() widget} is detached from the UI.
   */
  void removeFromParent();

  /**
   * This method gets called whenever the parent of the according
   * {@link net.sf.mmm.client.ui.api.widget.UiWidget} is changed. It does nothing by default but may be
   * overridden to deal with special behavior of native UI toolkits.
   * 
   * @param parent is the new parent or <code>null</code> if {@link #removeFromParent() removed from parent}.
   */
  void setParent(UiWidgetComposite<?> parent);

  /**
   * This method is called from {@link net.sf.mmm.client.ui.base.widget.UiModeChanger} for
   * {@link net.sf.mmm.client.ui.api.widget.UiWidget#setMode(net.sf.mmm.client.ui.api.common.UiMode)}. It only
   * handles the predefined {@link net.sf.mmm.client.ui.api.common.UiMode}s.
   * 
   * @param editMode - <code>true</code> of {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT} and
   *        <code>false</code> for {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW}.
   */
  void setMode(boolean editMode);

  //
  // /**
  // * This method gets the {@link UiConfiguration}. It is used to implement configuration specific features.
  // *
  // * @return the {@link UiConfiguration}
  // */
  // UiConfiguration getConfiguration();
  //
  // /**
  // * This method sets the {@link #getConfiguration() configuration}.<br/>
  // * <b>ATTENTION:</b><br/>
  // * This method is automatically called from
  // * {@link AbstractUiWidgetReal#getWidgetAdapter(AbstractUiWidgetReal)}. It must be called only once.
  // *
  // * @param configuration is the value for {@link #getConfiguration()}.
  // */
  // void setConfiguration(UiConfiguration configuration);

  /**
   * This method creates an absolute URL from the given <code>relativePath</code>.
   * 
   * @param relativePath is the relative path (URL suffix) to the image.
   * 
   * @see net.sf.mmm.client.ui.api.widget.UiConfiguration#getTheme()
   * 
   * @return the absolute URL.
   */
  String createAbsoluteImageUrl(String relativePath);

  /**
   * This method creates the label for this widget. Depending on the underlying toolkit implementation the
   * native widget may already contain the label (applies e.g. for <code>FormItem</code> in SmartGWT). In such
   * case this method will wrap it as {@link AbstractUiWidgetLabel}. Otherwise this method should
   * {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#create(Class) create} a new
   * {@link AbstractUiWidgetLabel} and return it.
   * 
   * @param context is the {@link UiContext} that may be used to create the label.
   * @return the label.
   */
  AbstractUiWidgetLabel<?> createLabel(UiContext context);

}
