/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the text of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteText extends UIReadText {

  /**
   * This method sets the text of this object.
   * 
   * @param text
   *        is the new text for this object.
   */
  void setText(String text);

}