/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomButtonPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetButtonGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetImageGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetLabelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetTabGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetComboBoxGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetDateFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetDoubleFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetIntegerFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetIntegerRangeFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetLongFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetPasswordFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRadioButtonsGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRadioButtonsVerticalGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRichTextAreaGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetTextAreaGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetTextFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuBarGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuItemClickableGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuItemSeparatorGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetBorderPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetGridCellGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetGridPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetGridRowGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetHorizontalPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetTabPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetVerticalPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.window.UiWidgetMainWindowGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.window.UiWidgetPopupGwt;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import com.google.gwt.user.client.ui.UIObject;

/**
 * This is the implementation of the {@link net.sf.mmm.client.ui.api.UiContext} for GWT widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// wont help but for consistency... maybe GIN will support it once...
@Named(UiWidgetFactoryNative.CDI_NAME)
public class UiWidgetFactoryNativeGwt extends AbstractUiWidgetFactoryNative {

  /** @see #getMainWindow() */
  private UiWidgetMainWindow mainWindow;

  /**
   * The constructor.
   */
  public UiWidgetFactoryNativeGwt() {

    super();
    // core
    register(new UiWidgetImageGwt.Factory());
    register(new UiWidgetButtonGwt.Factory());
    register(new UiWidgetLabelGwt.Factory());
    // field
    register(new UiWidgetTextFieldGwt.Factory());
    register(new UiWidgetPasswordFieldGwt.Factory());
    register(new UiWidgetTextAreaGwt.Factory());
    register(new UiWidgetRichTextAreaGwt.Factory());
    register(new UiWidgetLongFieldGwt.Factory());
    register(new UiWidgetIntegerFieldGwt.Factory());
    register(new UiWidgetIntegerRangeFieldGwt.Factory());
    register(new UiWidgetDoubleFieldGwt.Factory());
    register(new UiWidgetDateFieldGwt.Factory());
    register(new UiWidgetRadioButtonsGwt.Factory());
    register(new UiWidgetRadioButtonsVerticalGwt.Factory());
    register(new UiWidgetComboBoxGwt.Factory());
    // panels
    register(new UiWidgetTabGwt.Factory());
    register(new UiWidgetTabPanelGwt.Factory());
    register(new UiWidgetHorizontalPanelGwt.Factory());
    register(new UiWidgetVerticalPanelGwt.Factory());
    register(new UiWidgetGridPanelGwt.Factory());
    register(new UiWidgetGridRowGwt.Factory());
    register(new UiWidgetGridCellGwt.Factory());
    register(new UiWidgetBorderPanelGwt.Factory());
    register(new UiWidgetCustomButtonPanel.Factory());
    // menu
    register(new UiWidgetMenuBarGwt.Factory());
    register(new UiWidgetMenuGwt.Factory());
    register(new UiWidgetMenuItemClickableGwt.Factory());
    register(new UiWidgetMenuItemSeparatorGwt.Factory());
    // window
    register(new UiWidgetPopupGwt.Factory());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMainWindow getMainWindow() {

    if (this.mainWindow == null) {
      this.mainWindow = createMainWindow();
    }
    return this.mainWindow;
  }

  /**
   * @return a new instance of {@link UiWidgetMainWindow}.
   */
  private UiWidgetMainWindow createMainWindow() {

    AbstractUiContext context = getContext();
    NlsNullPointerException.checkNotNull(UiContext.class, context);
    UiWidgetMainWindowGwt window = new UiWidgetMainWindowGwt(context);
    window.setVisible(true);
    return window;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UIObject getNativeWidget(UiWidgetRegular widget) {

    return (UIObject) super.getNativeWidget(widget);
  }
}
