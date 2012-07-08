/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.widget.gwt;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetButton;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactoryPlain;
import net.sf.mmm.ui.toolkit.impl.widget.gwt.atomic.UiWidgetButtonGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} for GWT widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFactoryGwt extends AbstractUiWidgetFactoryPlain<Widget> {

  /**
   * The constructor.
   */
  public UiWidgetFactoryGwt() {

    super();
    register(new AbstractUiSingleWidgetFactory<UiWidgetButton>(UiWidgetButton.class) {

      public UiWidgetButton create() {

        return new UiWidgetButtonGwt();
      }

    });
  }

}
