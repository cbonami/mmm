/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the {@link #isMaximized() maximized}
 * state of an {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIWindow
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadMaximized {

  /**
   * This method determines if this object is maximized.
   * 
   * @return <code>true</code> if the object is maximized,
   *         <code>false</code> otherwise.
   */
  boolean isMaximized();

}
