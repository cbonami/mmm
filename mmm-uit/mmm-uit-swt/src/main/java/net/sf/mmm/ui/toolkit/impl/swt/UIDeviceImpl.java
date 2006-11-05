/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.widgets.Monitor;

import net.sf.mmm.ui.toolkit.api.UIDevice;

/**
 * This class is the implementation of the UIDevice interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDeviceImpl implements UIDevice {

  /** the SWT graphics device */
  private Monitor device;

  /**
   * The constructor.
   * 
   * @param monitor
   *        is the SWT graphics device to represent.
   */
  public UIDeviceImpl(Monitor monitor) {

    super();
    this.device = monitor;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return this.device.toString();
  }
}
