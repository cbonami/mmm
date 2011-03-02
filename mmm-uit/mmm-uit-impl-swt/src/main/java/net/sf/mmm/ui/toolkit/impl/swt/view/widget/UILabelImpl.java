/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncLabelAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UILabelImpl extends AbstractUIWidget implements UiLabel {

  /** the synchron access to the label */
  private final SyncLabelAccess syncAccess;

  /** the icon */
  private UiImageImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UILabelImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    int style = SWT.SHADOW_NONE | SWT.LEFT;
    this.syncAccess = new SyncLabelAccess(uiFactory, style);
    this.icon = null;
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
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
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
  public String getValue() {

    return this.syncAccess.getText();
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

}
