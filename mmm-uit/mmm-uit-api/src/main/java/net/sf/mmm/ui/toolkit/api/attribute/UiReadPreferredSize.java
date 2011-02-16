/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject object} of the UI framework that
 * has a preferred size.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiReadPreferredSize {

  /**
   * This method gets the preferred width of this object in pixels.
   * 
   * @return the width.
   */
  int getPreferredWidth();

  /**
   * This method gets the preferred height of this object in pixels.
   * 
   * @return the height.
   */
  int getPreferredHeight();

}
