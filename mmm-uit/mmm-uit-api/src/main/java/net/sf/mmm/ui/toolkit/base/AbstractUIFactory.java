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
import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;
import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryRenamed} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIFactory implements UIFactoryRenamed {

  /** the disposed flag */
  private boolean disposed;

  /** @see #getLocale() */
  private Locale locale;

  /** @see #getScriptOrientation() */
  private ScriptOrientation scriptOrientation;

  /** @see #getScriptOrientation() */
  private ScriptOrientation designOrientation;

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
    this.designOrientation = ScriptOrientation.LEFT_TO_RIGHT;
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

    if (this.scriptOrientation != scriptOrientation) {
      this.scriptOrientation = scriptOrientation;
      refresh(UIRefreshEvent.ORIENTATION_MODIFIED);
    }
  }

  /**
   * {@inheritDoc}
   */
  public ScriptOrientation getDesignOrientation() {

    return this.designOrientation;
  }

  /**
   * {@inheritDoc}
   */
  public void setDesignOrientation(ScriptOrientation orientation) {

    this.designOrientation = orientation;
  }

  /**
   * This method determines if the vertical orientation of the GUI should be
   * inverted (mirrored).
   * 
   * @see #getScriptOrientation()
   * @see #getDesignOrientation()
   * 
   * @return <code>true</code> for inverse orientation, <code>false</code>
   *         for designed orientation.
   */
  public boolean isFlipVertical() {

    return (getScriptOrientation().isHorizontal() != getDesignOrientation().isHorizontal());
  }

  /**
   * This method determines if the horizontal orientation of the GUI should be
   * inverted (mirrored).
   * 
   * @see #getScriptOrientation()
   * @see #getDesignOrientation()
   * 
   * @return <code>true</code> for inverse orientation, <code>false</code>
   *         for designed orientation.
   */
  public boolean isFlipHorizontal() {

    return (getScriptOrientation().isLeftToRight() != getDesignOrientation().isLeftToRight());
  }

  /**
   * This method refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.window.UIWindow windows} created by this
   * factory. The refresh of a window recursively refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.UiNode nodes} contained in the window.
   * This way all visible GUI elements are refreshed.
   * 
   * @param event is the event with details about the refresh.
   */
  public void refresh(UIRefreshEvent event) {

    AbstractUIWindow[] currentWindows;
    synchronized (this.windows) {
      currentWindows = this.windows.toArray(new AbstractUIWindow[this.windows.size()]);
    }
    for (AbstractUIWindow window : currentWindows) {
      window.refresh(event);
    }
  }

  /**
   * This method adds (registers) the given <code>window</code> to this
   * factory.
   * 
   * @param window is the window to add.
   */
  public void addWindow(AbstractUIWindow window) {

    synchronized (this.windows) {
      this.windows.add(window);
    }
  }

  /**
   * This method removes (de-registers) the given <code>window</code> from
   * this factory.
   * 
   * @param window is the window to remove.
   */
  public void removeWindow(AbstractUIWindow window) {

    synchronized (this.windows) {
      this.windows.remove(window);
    }
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
  public UiLabel createLabel() {

    return createLabel("");
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField() {

    return createTextField(true);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text, ButtonStyle style) {

    return createButton(text, null, style);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(UiImage icon, ButtonStyle style) {

    return createButton(null, icon, style);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(Action action) {

    UiButton button = createButton(action.getName(), action.getButtonStyle());
    button.addActionListener(action.getActionListener());
    UiImage icon = action.getIcon();
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
  public UiSlicePanel createPanel(Orientation orientation) {

    return createPanel(orientation, null);
  }

  /**
   * {@inheritDoc}
   */
  public <C extends UiElement> UiDecoratedComponent<UiLabel, C> createLabeledComponent(
      String label, C component) {

    return createDecoratedComponent(createLabel(label), component);
  }

  /**
   * {@inheritDoc}
   */
  public UiDecoratedComponent<UiLabel, UiSlicePanel> createLabeledComponents(String label,
      UiElement... components) {

    UiSlicePanel panel = createPanel(Orientation.HORIZONTAL);
    for (UiElement component : components) {
      panel.addComponent(component);
    }
    return createLabeledComponent(label, panel);
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createPicture(File imageFile) throws IOException {

    try {
      return createPicture(imageFile.toURI().toURL());
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model) {

    return createSlideBar(model, Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiList<E> createList(UiListMvcModel<E> model) {

    return createList(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model) {

    return createComboBox(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar() {

    return createProgressBar(Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public UiScrollPanel createScrollPanel() {

    return createScrollPanel(null);
  }

  /**
   * {@inheritDoc}
   */
  public UiTable<?> createTable() {

    return createTable(false);
  }

  /**
   * {@inheritDoc}
   */
  public UiTable<?> createTable(boolean multiSelection) {

    return createTable(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public UiTree<?> createTree() {

    return createTree(false);
  }

  /**
   * {@inheritDoc}
   */
  public UiTree<?> createTree(boolean multiSelection) {

    return createTree(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UiElement component) {

    // TODO: i18n
    return createPrintAction(component, "Print");
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UiElement component, String actionName) {

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
