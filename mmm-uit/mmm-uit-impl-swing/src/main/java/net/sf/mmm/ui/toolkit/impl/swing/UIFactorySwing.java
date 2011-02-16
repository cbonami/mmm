/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UIDisplay;
import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUIFactory;
import net.sf.mmm.ui.toolkit.impl.awt.UIDeviceImpl;
import net.sf.mmm.ui.toolkit.impl.awt.UIDisplayImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIDecoratedComponentImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UISlicePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UISplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UITabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UILabelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIListImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITableImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITreeImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.editor.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbenchImpl;

/**
 * This class is the implementation of the UIFactory interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFactorySwing extends AbstractUIFactory {

  /** the default display */
  private UIDisplayImpl display;

  /**
   * The dummy constructor.
   * 
   * This constructor may be used for testing if an instance is required for the
   * default display without using the
   * {@link net.sf.mmm.ui.toolkit.api.UIServiceRenamed UIService}.
   */
  public UIFactorySwing() {

    super();
    GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
    UIDeviceImpl uiDevice = new UIDeviceImpl(defaultDevice);
    this.display = new UIDisplayImpl(this, uiDevice, defaultDevice.getDefaultConfiguration());
  }

  /**
   * The constructor.
   * 
   * @param uiDevice is the device the display of this factory belongs to.
   * @param graphicConfiguration is the graphics configuration for the display
   *        to represent.
   */
  public UIFactorySwing(UIDeviceImpl uiDevice, GraphicsConfiguration graphicConfiguration) {

    super();
    this.display = new UIDisplayImpl(this, uiDevice, graphicConfiguration);
  }

  /**
   * {@inheritDoc}
   */
  public UIDisplay getDisplay() {

    return this.display;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactoryRenamed#getDisplay()
   * 
   * @return the AWT display.
   */
  public GraphicsConfiguration getAwtGc() {

    return this.display.getGraphicsConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    UIFrameImpl frame = new UIFrameImpl(this, null, title, resizeable);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(String text, UiImage icon, ButtonStyle style) {

    UIButton button = new UIButtonImpl(this, null, style);
    button.setText(text);
    if (icon != null) {
      button.setIcon(icon);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public UISlicePanel createPanel(Orientation orientation, String text) {

    UISlicePanelImpl panel = new UISlicePanelImpl(this, null, orientation);
    panel.setBorderTitle(text);
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public <D extends UiElement, C extends UiElement> UIDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component) {

    UIDecoratedComponent<D, C> decoratedComponent = new UIDecoratedComponentImpl<D, C>(this, null);
    decoratedComponent.setDecorator(decorator);
    decoratedComponent.setComponent(component);
    return decoratedComponent;
  }

  /**
   * {@inheritDoc}
   */
  public UIScrollPanel createScrollPanel(UIComposite child) {

    UIScrollPanel scrollPanel = new UIScrollPanelImpl(this, null);
    scrollPanel.setComponent(child);
    return scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIList<E> createList(UIListModel<E> model, boolean multiSelection) {

    UIListImpl<E> list = new UIListImpl<E>(this, null);
    list.setMultiSelection(multiSelection);
    if (model != null) {
      list.setModel(model);
    }
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model, boolean editableFlag) {

    UIComboBox<E> comboBox = new UIComboBoxImpl<E>(this, null);
    comboBox.setEditable(editableFlag);
    if (model != null) {
      comboBox.setModel(model);
    }
    return comboBox;
  }

  /**
   * {@inheritDoc}
   */
  public <N> UITree<N> createTree(boolean multiSelection, UITreeModel<N> model) {

    UITreeImpl<N> tree = new UITreeImpl<N>(this, null);
    tree.setMultiSelection(multiSelection);
    if (model != null) {
      tree.setModel(model);
    }
    return tree;
  }

  /**
   * {@inheritDoc}
   */
  public <C> UITable<C> createTable(boolean multiSelection, UITableModel<C> model) {

    UITableImpl<C> table = new UITableImpl<C>(this, null);
    // table.setMultiSelection(multiSelection);
    if (model != null) {
      table.setModel(model);
    }
    return table;
  }

  /**
   * {@inheritDoc}
   */
  public UILabel createLabel(String text) {

    UILabel label = new UILabelImpl(this, null);
    label.setText(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UITextField createTextField(boolean editable) {

    UITextFieldImpl textField = new UITextFieldImpl(this, null);
    textField.setEditable(editable);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISpinBox<E> createSpinBox(UIListModel<E> model) {

    return new UISpinBoxImpl<E>(this, null, model);
  }

  /**
   * {@inheritDoc}
   */
  public UIFileDownload createFileDownload(FileAccess access) {

    return new UIFileDownloadImpl(this, null, access);
  }

  /**
   * {@inheritDoc}
   */
  public UIFileUpload createFileUpload() {

    return new UIFileUploadImpl(this, null);
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createPicture(URL imageUrl) {

    return new UIPictureImpl(this, imageUrl);
  }

  /**
   * {@inheritDoc}
   */
  public UITabbedPanel createTabbedPanel() {

    return new UITabbedPanelImpl(this, null);
  }

  /**
   * {@inheritDoc}
   */
  public UISplitPanel createSplitPanel(Orientation orientation) {

    return new UISplitPanelImpl(this, null, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model, Orientation orientation) {

    return new UISlideBarImpl<E>(this, null, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UIProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, null, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public UIWorkbench createWorkbench(String title) {

    UIWorkbenchImpl workbench = new UIWorkbenchImpl(this, title, true);
    addWindow(workbench);
    return workbench;
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UiElement component, String actionName, String jobName) {

    return new PrintAction((AbstractUIComponent) component, actionName, jobName);
  }

  /**
   * {@inheritDoc}
   */
  public UIDateEditor createDateEditor() {

    return new UIDateEditorImpl(this, null);
  }

}
