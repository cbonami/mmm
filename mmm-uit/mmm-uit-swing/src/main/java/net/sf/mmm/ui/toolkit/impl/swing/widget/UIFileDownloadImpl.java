/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.base.feature.FileAccessUtil;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileDownload} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileDownloadImpl extends AbstractUIWidget implements UIFileDownload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

      UIFileDownloadImpl.this.fileChooser.setDialogTitle(getText());
      int selection = UIFileDownloadImpl.this.fileChooser.showSaveDialog(UIFileDownloadImpl.this.button);
      if (selection == JFileChooser.APPROVE_OPTION) {
        FileAccessUtil.save(UIFileDownloadImpl.this.access, UIFileDownloadImpl.this.fileChooser
            .getSelectedFile(), getParentWindow());
      }
    }

  }

  /** the access to the downloadable data */
  private final FileAccess access;

  /** the widget used to present the download */
  private final JButton button;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param fileAccess
   *        gives access to the data that is offered for download.
   */
  public UIFileDownloadImpl(UIFactorySwing uiFactory, UINode parentObject, FileAccess fileAccess) {

    super(uiFactory, parentObject);
    this.access = fileAccess;
    this.fileChooser = new JFileChooser();
    this.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    // this.fileChooser.setMultiSelectionEnabled(false);
    this.fileChooser.setSelectedFile(new File(this.access.getFilename()));
    // TODO: i18n
    this.button = new JButton("Save");
    this.button.setToolTipText("Save " + this.access.getFilename());
    this.button.addActionListener(new Listener());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.button;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.button.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.button.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

}
