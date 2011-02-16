/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.mmm.ui.toolkit.base.AbstractUIPicture;

/**
 * This is the implementation of the {@link net.sf.mmm.ui.toolkit.api.UiImage}
 * interface using Swing as the underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPictureImpl extends AbstractUIPicture {

  /** the wrapped picture */
  private final ImageIcon picture;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param imageUrl is the URL to the image data.
   */
  public UIPictureImpl(UIFactorySwing uiFactory, URL imageUrl) {

    super(uiFactory);
    this.picture = new ImageIcon(imageUrl);
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return this.picture.getIconWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return this.picture.getIconHeight();
  }

  /**
   * This method gets the native Swing picture.
   * 
   * @return the Swing icon.
   */
  public ImageIcon getSwingIcon() {

    return this.picture;
  }

}
