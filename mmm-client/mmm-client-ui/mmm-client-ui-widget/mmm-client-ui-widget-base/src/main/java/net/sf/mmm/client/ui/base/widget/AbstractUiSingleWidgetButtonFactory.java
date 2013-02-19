/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the abstract base implementation of {@link UiSingleWidgetButtonFactory}.
 * 
 * @param <HANDLER> is the generic type of the {@link #getHandlerInterface() handler interface}. E.g.
 *        {@link net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiSingleWidgetButtonFactory<HANDLER extends UiHandlerPlain> implements
    UiSingleWidgetButtonFactory<HANDLER> {

  /**
   * The constructor.
   */
  public AbstractUiSingleWidgetButtonFactory() {

    super();
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  protected NlsBundleClientUiRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
  }

  /**
   * Creates a new {@link UiWidgetButton}.
   * 
   * @param context is the {@link UiContext}.
   * @param label is the optional label.
   * @param handler is the required click handler.
   * @param tooltip is the optional tooltip.
   * @param icon is the optional icon.
   * @return the new widget.
   */
  protected UiWidgetButton createButton(UiContext context, NlsMessage label, UiHandlerEventClick handler,
      NlsMessage tooltip, UiWidgetImage icon) {

    String labelText = "";
    if (label != null) {
      labelText = label.getLocalizedMessage();
    }
    UiWidgetButton button = context.getWidgetFactoryAdvanced().createButton(labelText, handler);
    if (tooltip != null) {
      button.setTooltip(tooltip.getLocalizedMessage());
    }
    if (icon != null) {
      button.setImage(icon);
    }
    return button;
  }

}
