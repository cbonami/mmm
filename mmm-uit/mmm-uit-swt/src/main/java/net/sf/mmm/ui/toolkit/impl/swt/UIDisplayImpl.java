/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import net.sf.mmm.ui.toolkit.base.AbstractUIDisplay;

/**
 * This class is the implementation of the UIDisplay interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDisplayImpl extends AbstractUIDisplay {

  /**
   * This inner class is used to get the display size.
   */
  private static final class SizeGetter implements Runnable {

    /** the size */
    private Rectangle size;

    /** the display */
    private Display display;

    /**
     * The constructor.
     * 
     * @param d is the display
     */
    private SizeGetter(Display d) {

      this.display = d;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {

      this.size = this.display.getBounds();
    }

  }

  /** the SWT display */
  private final Display display;

  /** the task used to get the display size */
  private final SizeGetter sizeGetter;

  /** the ui factory */
  private final UIFactorySwt factory;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param uiDevice is the device the display belongs to.
   * @param swtDisplay is the SWT display to wrap.
   */
  public UIDisplayImpl(UIFactorySwt uiFactory, UIDeviceImpl uiDevice, Display swtDisplay) {

    super(uiFactory, uiDevice);
    this.factory = uiFactory;
    this.display = swtDisplay;
    this.sizeGetter = new SizeGetter(this.display);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UIFactorySwt getFactory() {

    return this.factory;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    invokeSynchron(this.sizeGetter);
    return this.sizeGetter.size.width;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    invokeSynchron(this.sizeGetter);
    return this.sizeGetter.size.height;
  }

  /**
   * This method gets the unwrapped display object.
   * 
   * @return the SWT display object.
   */
  public Display getSwtDisplay() {

    return this.display;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void dispatch() {

    if (isDispatchThread()) {
      if (!this.display.readAndDispatch()) {
        this.display.sleep();
      }
    } else {
      // sleep some seconds...
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDispatchThread() {

    return (this.display.getThread() == Thread.currentThread());
  }

  /**
   * {@inheritDoc}
   */
  public void invokeAsynchron(Runnable task) {

    this.display.asyncExec(task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doInvokeSynchron(Runnable task) {

    this.display.syncExec(task);
  }

}
