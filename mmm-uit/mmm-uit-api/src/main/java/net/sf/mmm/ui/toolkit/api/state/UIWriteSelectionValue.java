/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the selected
 * {@link #getSelectedValue() value} of an object.
 * 
 * @param <V>
 *        is the templated type of the selectable value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteSelectionValue<V> extends UIReadSelectionValue<V> {

  /**
   * This method sets the value currently selected. If the given value is NOT
   * available, the method will have NO effect.
   * 
   * @param newValue
   *        is the newValue to select.
   */
  void setSelectedValue(V newValue);

}
