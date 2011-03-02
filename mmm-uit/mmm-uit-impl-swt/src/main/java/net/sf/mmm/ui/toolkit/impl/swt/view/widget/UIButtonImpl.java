/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncButtonAccess;

import org.eclipse.swt.SWT;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIButtonImpl extends AbstractUIWidget implements UiButton {

  /** the style of the button */
  private final ButtonStyle style;

  /** the synchronous access to the button */
  private final SyncButtonAccess syncAccess;

  /** the icon */
  private UiImageImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param buttonStyle determines the style of the button.
   */
  public UIButtonImpl(UiFactorySwt uiFactory, ButtonStyle buttonStyle) {

    super(uiFactory);
    this.style = buttonStyle;
    int swtStyle = UiFactorySwt.convertButtonStyle(buttonStyle);
    this.syncAccess = new SyncButtonAccess(uiFactory, swtStyle);
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return this.syncAccess.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.syncAccess.setSelected(selected);
  }

  /**
   * {@inheritDoc}
   */
  public UiImageImpl getImage() {

    return this.icon;
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.icon = (UiImageImpl) newIcon;
    if (this.icon == null) {
      this.syncAccess.setImage(null);
    } else {
      this.syncAccess.setImage(this.icon.getSwtImage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncButtonAccess getSyncAccess() {

    return this.syncAccess;
  }

}
