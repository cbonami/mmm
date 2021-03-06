/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncButtonAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncFileDialogAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileDownloadImpl extends AbstractUiWidgetSwt<Button> implements UiFileDownload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class SelectionListener implements Listener {

    /**
     * {@inheritDoc}
     */
    public void handleEvent(Event event) {

      UIFileDownloadImpl.this.syncDialgAccess.setParentAccess(UIFileDownloadImpl.this.syncAccess);
      UIFileDownloadImpl.this.syncDialgAccess.setText(getValue());
      String file = UIFileDownloadImpl.this.syncDialgAccess.open();
      if (file != null) {
        // TODO

        // FileAccessUtil.save(UIFileDownloadImpl.this.uiFileAccess, new
        // File(file), getParentWindow());
      }
    }

  }

  /** sync access to the widget used to present the download */
  private final SyncButtonAccess syncAccess;

  /** sync access to the file dialog used to choose the saving destiaiton */
  private final SyncFileDialogAccess syncDialgAccess;

  /** the access to the downloadable data */
  private final UiFileAccess uiFileAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param access gives access to the data that is offered for download.
   */
  public UIFileDownloadImpl(UiFactorySwt uiFactory, UiFileAccess access) {

    super(uiFactory);
    this.uiFileAccess = access;
    this.syncAccess = new SyncButtonAccess(uiFactory, this, SWT.DEFAULT);
    this.syncAccess.setText("Save");
    this.syncAccess.addListener(SWT.Selection, new SelectionListener());
    this.syncDialgAccess = new SyncFileDialogAccess(uiFactory, this, SWT.SAVE);
    // TODO: filename
    this.syncDialgAccess.setFilename(this.uiFileAccess.getUrl());
    // this.fileChooser = new FileDialog(getSwtParentShell(), SWT.SAVE);
    // this.fileChooser.setFileName(this.access.getFilename());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractSyncControlAccess<Button> getAdapter() {

    return this.syncAccess;
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
  public void setValue(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

}
