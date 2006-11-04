/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteIcon;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag;

/**
 * This is the interface for a button.<br>
 * According to its {@link #getStyle() style} this can be a
 * {@link net.sf.mmm.ui.toolkit.api.widget.ButtonStyle#RADIO radio-},
 * {@link net.sf.mmm.ui.toolkit.api.widget.ButtonStyle#CHECK checkbox-},
 * {@link net.sf.mmm.ui.toolkit.api.widget.ButtonStyle#TOGGLE toggle-}, or
 * {@link net.sf.mmm.ui.toolkit.api.widget.ButtonStyle#DEFAULT regular} button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIButton extends UIWidget, UILabel, UIWriteSelectionFlag, UIWriteIcon {

  /** the type of this object */
  String TYPE = "Button";

  /**
   * This method gets the style of this button. The style determines the
   * visualization and the behaviour of the button.
   * 
   * @return the button style.
   */
  ButtonStyle getStyle();

}
