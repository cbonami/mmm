/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.ui.toolkit.api.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIFactory implements UIFactory {

  /** the disposed flag */
  private boolean disposed;

  /** @see #getLocale() */
  private Locale locale;

  /** @see #getScriptOrientation() */
  private ScriptOrientation scriptOrientation;

  /** The list of all windows that have been created by this factory */
  private List<AbstractUIWindow> windows;

  /**
   * The constructor.
   */
  public AbstractUIFactory() {

    super();
    // TODO: do we need a thread-safe implementation here?
    this.windows = new ArrayList<AbstractUIWindow>();
    this.disposed = false;
    this.locale = Locale.getDefault();
    // TODO: set from default locale!
    this.scriptOrientation = ScriptOrientation.LEFT_TO_RIGHT;
  }

  /**
   * {@inheritDoc}
   */
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * {@inheritDoc}
   */
  public void setLocale(Locale locale) {

    this.locale = locale;
    // TODO: update script-orientation from resource-bundle
  }

  /**
   * {@inheritDoc}
   */
  public ScriptOrientation getScriptOrientation() {

    return this.scriptOrientation;
  }

  /**
   * {@inheritDoc}
   */
  public void setScriptOrientation(ScriptOrientation scriptOrientation) {

    this.scriptOrientation = scriptOrientation;
    // TODO cause refresh of all windows...
    refresh();
  }

  /**
   * This method refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.window.UIWindow windows} created by this
   * factory. The refresh of a window recursively refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.UINode nodes} contained in the window.
   * This way all visible GUI elements are refreshed.
   */
  public void refresh() {

    for (AbstractUIWindow window : this.windows) {
      window.refresh();
    }
  }

  /**
   * This method registers the given <code>window</code> to this factory.
   * 
   * @param window
   *        is the window to add.
   */
  public void addWindow(AbstractUIWindow window) {

    this.windows.add(window);
  }

  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title) {

    return createFrame(title, true);
  }

  /**
   * {@inheritDoc}
   */
  public UILabel createLabel() {

    return createLabel("");
  }

  /**
   * {@inheritDoc}
   */
  public UITextField createTextField() {

    return createTextField(true);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(String text, ButtonStyle style) {

    return createButton(text, null, style);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(UIPicture icon, ButtonStyle style) {

    return createButton(null, icon, style);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(Action action) {

    UIButton button = createButton(action.getName(), action.getButtonStyle());
    button.addActionListener(action.getActionListener());
    UIPicture icon = action.getIcon();
    if (icon != null) {
      button.setIcon(icon);
    }
    String id = action.getId();
    if (id != null) {
      button.setId(id);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public UISlicePanel createPanel(Orientation orientation) {

    return createPanel(orientation, null);
  }

  /**
   * {@inheritDoc}
   */
  public <C extends UIComponent> UIDecoratedComponent<UILabel, C> createLabeledComponent(
      String label, C component) {

    return createDecoratedComponent(createLabel(label), component);
  }

  /**
   * {@inheritDoc}
   */
  public UIDecoratedComponent<UILabel, UISlicePanel> createLabeledComponents(String label,
      UIComponent... components) {

    UISlicePanel panel = createPanel(Orientation.HORIZONTAL);
    for (UIComponent component : components) {
      panel.addComponent(component);
    }
    return createLabeledComponent(label, panel);
  }

  /**
   * {@inheritDoc}
   */
  public UIPicture createPicture(File imageFile) throws IOException {

    try {
      return createPicture(imageFile.toURL());
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model) {

    return createSlideBar(model, Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIList<E> createList(UIListModel<E> model) {

    return createList(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model) {

    return createComboBox(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public UIProgressBar createProgressBar() {

    return createProgressBar(Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public UIScrollPanel createScrollPanel() {

    return createScrollPanel(null);
  }

  /**
   * {@inheritDoc}
   */
  public UITable<?> createTable() {

    return createTable(false);
  }

  /**
   * {@inheritDoc}
   */
  public UITable<?> createTable(boolean multiSelection) {

    return createTable(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public UITree<?> createTree() {

    return createTree(false);
  }

  /**
   * {@inheritDoc}
   */
  public UITree<?> createTree(boolean multiSelection) {

    return createTree(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UIComponent component) {

    // TODO: i18n
    return createPrintAction(component, "Print");
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UIComponent component, String actionName) {

    if (component == null) {
      throw new IllegalArgumentException("Component must NOT be null!");
    }
    return createPrintAction(component, actionName, actionName + " " + component.getId());
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.disposed;
  }

}
