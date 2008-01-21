/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the size of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSize {

  /**
   * This method gets the width of this object in pixels.
   * 
   * @return the width.
   */
  int getWidth();

  /**
   * This method gets the height of this object in pixels.
   * 
   * @return the height.
   */
  int getHeight();

}
