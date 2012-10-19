/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectDisposedException;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationStateImpl;

/**
 * This is the abstract base implementation of {@link UiWidget}. It is independent of any native toolkit via
 * {@link UiWidgetAdapter} that is {@link #getWidgetAdapter() lazily created}.<br/>
 * If you want to create an implementation of all the {@link UiWidget}s for a native UI toolkit, you are
 * strongly encouraged to extend from this class and its subclasses (all classes named
 * <code>AbstractUiWidget*</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidget<ADAPTER extends UiWidgetAdapter<?>> implements UiWidget {

  /** @see #getFactory() */
  private final AbstractUiWidgetFactory<?> factory;

  /** @see #getWidgetAdapter() */
  private ADAPTER widgetAdapter;

  /** @see #getParent() */
  private UiWidgetComposite<?> parent;

  /** @see #getId() */
  private String id;

  /** @see #isVisible() */
  private boolean visible;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #isDisposed() */
  private boolean disposed;

  /** @see #getTooltip() */
  private String tooltip;

  /** @see #getStyles() */
  private String styles;

  /** @see #getWidth() */
  private String width;

  /** @see #getHeight() */
  private String height;

  /** @see #getAriaRole() */
  private String ariaRole;

  /** @see #getMode() */
  private UiMode mode;

  /** @see #getModeFixed() */
  private UiMode modeFixed;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidget(AbstractUiWidgetFactory<?> factory) {

    super();
    this.factory = factory;
    this.visible = true;
    this.enabled = true;
    this.styles = "";
  }

  /**
   * @return the factory
   */
  @Override
  public AbstractUiWidgetFactory<?> getFactory() {

    return this.factory;
  }

  /**
   * @return <code>true</code> if the {@link UiWidgetAdapter} has already been {@link #getWidgetAdapter()
   *         created}. Otherwise <code>false</code> (if {@link #getWidgetAdapter()} has never been called
   *         yet).
   */
  protected final boolean hasWidgetAdapter() {

    return (this.widgetAdapter != null);
  }

  /**
   * This method gets or creates the {@link UiWidgetAdapter}.<br/>
   * <b>ATTENTION:</b><br/>
   * On the first call of this method, the {@link UiWidgetAdapter} is created. For the purpose of lazy
   * instantiation this should happen as late as possible. Use {@link #hasWidgetAdapter()} to prevent
   * unnecessary creation.
   * 
   * @return the {@link UiWidgetAdapter}.
   */
  protected final ADAPTER getWidgetAdapter() {

    if (this.widgetAdapter == null) {
      if (this.disposed) {
        throw new ObjectDisposedException(this);
      }
      this.widgetAdapter = createWidgetAdapter();
      this.widgetAdapter.setConfiguration(this.factory.getConfiguration());
      initializeWidgetAdapter(this.widgetAdapter);
    }
    return this.widgetAdapter;
  }

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param <A> is the generic type of {@link #getWidgetAdapter()}.
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final <A extends UiWidgetAdapter<?>> A getWidgetAdapter(AbstractUiWidget<A> widget) {

    return widget.getWidgetAdapter();
  }

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final UiWidgetAdapter<?> getWidgetAdapter(UiWidget widget) {

    return asAbstractWidget(widget).getWidgetAdapter();
  }

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  public static void setParent(UiWidget widget, UiWidgetComposite<?> newParent) {

    asAbstractWidget(widget).setParent(newParent);
  }

  /**
   * This method gets the given <code>widget</code> as {@link AbstractUiWidget}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method does more than a simple cast. Never cast manually to {@link AbstractUiWidget} but use this
   * method instead.
   * 
   * @param widget is the widget to "cast".
   * @return the (underlying) {@link AbstractUiWidget}.
   */
  protected static AbstractUiWidget<?> asAbstractWidget(UiWidget widget) {

    AbstractUiWidget<?> abstractWidget;
    if (widget instanceof UiWidgetCustom) {
      UiWidgetCustom<?, ?> customWidget = (UiWidgetCustom<?, ?>) widget;
      UiWidget delegate = AccessHelperWidgetCustom.getDelegateStatic(customWidget);
      return asAbstractWidget(delegate);
    } else {
      try {
        abstractWidget = (AbstractUiWidget<?>) widget;
      } catch (Exception e) {
        throw new NlsClassCastException(e, widget, AbstractUiWidget.class);
      }
    }
    return abstractWidget;
  }

  /**
   * This method invokes {@link #removeFromParent()} on the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget that should be removed from its {@link #getParent() parent}.
   */
  public static void removeFromParent(UiWidget widget) {

    asAbstractWidget(widget).removeFromParent();
  }

  /**
   * This method is called from {@link #getWidgetAdapter()} to initialize the {@link UiWidgetAdapter}. All
   * attributes of this widget need to be set in the {@link UiWidgetAdapter}.
   * 
   * @param adapter is the {@link UiWidgetAdapter} to initialize.
   */
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    adapter.setVisible(this.visible);
    adapter.setEnabled(this.enabled);
    if (this.parent != null) {
      adapter.setParent(this.parent);
    }
    if (this.id != null) {
      adapter.setId(this.id);
    }
    if (this.tooltip != null) {
      adapter.setTooltip(this.tooltip);
    }
    if (this.ariaRole != null) {
      adapter.setAriaRole(this.ariaRole);
    }
    if (!this.styles.isEmpty()) {
      adapter.setStyles(this.styles);
    }
    if (this.width != null) {
      if (this.height != null) {
        adapter.setSize(this.width, this.height);
      } else {
        adapter.setWidth(this.width);
      }
    } else if (this.height != null) {
      adapter.setHeight(this.height);
    }
  }

  /**
   * This method creates the {@link #getWidgetAdapter() widget adapter}.<br/>
   * This design is done to give more flexibility by overriding and also for lazy initialization of the
   * widget.
   * 
   * @return a new instance of the {@link #getWidgetAdapter() underlying widget}.
   */
  protected abstract ADAPTER createWidgetAdapter();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    if (((this.id == null) && (newId != null)) || (!this.id.equals(newId))) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setId(newId);
      }
      this.id = newId;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetComposite<?> getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected void setParent(UiWidgetComposite<?> parent) {

    this.parent = parent;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setParent(parent);
    }
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  protected void removeFromParent() {

    this.parent = null;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.removeFromParent();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiMode getMode() {

    return this.mode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setMode(UiMode mode) {

    if (this.mode == mode) {
      // mode not changed, nothing to do...
      return;
    }
    if (this.modeFixed != null) {
      // fixed mode prevents changing the mode...
      return;
    }
    this.factory.getModeChanger().changeMode(this, mode);
    this.mode = mode;
    setModeRecursive(mode);
  }

  /**
   * This method is called from {@link #setMode(UiMode)} to recursively change the {@link UiMode}.
   * 
   * @param newMode is the new {@link UiMode}.
   */
  void setModeRecursive(UiMode newMode) {

    // do nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiMode getModeFixed() {

    return this.modeFixed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setModeFixed(UiMode modeFixed) {

    if (this.modeFixed == modeFixed) {
      return;
    }
    this.modeFixed = null;
    setMode(modeFixed);
    this.modeFixed = modeFixed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    if ((!this.disposed) && (this.widgetAdapter != null)) {
      this.widgetAdapter.dispose();
      this.widgetAdapter = null;
    }
    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    if (this.visible != visible) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setVisible(visible);
      }
      this.visible = visible;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return this.visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisibleRecursive() {

    if (!this.visible) {
      return false;
    }
    if ((this.parent == null) || !this.parent.isVisible()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    if (hasWidgetAdapter()) {
      if (((this.tooltip == null) && (tooltip != null)) || (!this.tooltip.equals(tooltip))) {
        getWidgetAdapter().setTooltip(tooltip);
      }
    }
    this.tooltip = tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    if (this.enabled != enabled) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setEnabled(enabled);
      }
      this.enabled = enabled;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    if (!this.enabled) {
      return false;
    }
    if ((this.parent == null) || !this.parent.isEnabled()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWidth() {

    if ((this.width == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getWidth();
    }
    return this.width;
  }

  /**
   * @return the height
   */
  @Override
  public String getHeight() {

    if ((this.height == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getHeight();
    }
    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(String width) {

    this.width = width;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(width);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(String height) {

    this.height = height;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(height);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(String newWidth, String newHeight) {

    this.width = newWidth;
    this.height = newHeight;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setSize(newWidth, newHeight);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int newWidth, int newHeight) {

    setSize(newWidth + "px", newHeight + "px");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int widthInPixel) {

    setWidth(widthInPixel + "px");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int heightInPixel) {

    setHeight(heightInPixel + "px");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAriaRole() {

    return this.ariaRole;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAriaRole(String ariaRole) {

    this.ariaRole = ariaRole;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setAriaRole(ariaRole);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    return this.styles;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    NlsNullPointerException.checkNotNull("styles", styles);
    this.styles = styles;
    updateStyles();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryStyle() {

    if (this.styles.isEmpty()) {
      return null;
    } else {
      int index = this.styles.indexOf(' ');
      if (index >= 0) {
        return this.styles.substring(0, index);
      } else {
        return this.styles;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    if ((primaryStyle == null) || (primaryStyle.isEmpty())) {
      String currentPrimaryStyle = getPrimaryStyle();
      if (currentPrimaryStyle != null) {
        removeStyle(currentPrimaryStyle);
      }
      return;
    }
    if (this.styles.isEmpty()) {
      this.styles = primaryStyle;
    } else {
      int startIndex = getIndexOfStyle(primaryStyle);
      if (startIndex == 0) {
        // it is already the primary style - nothing to do...
        return;
      }
      StringBuilder buffer = new StringBuilder(primaryStyle);
      if (startIndex > 0) {
        // the primaryStyle is an existing style but NOT the primary style. We have to move it to the start.
        int endIndex = startIndex + primaryStyle.length();
        if (endIndex < this.styles.length()) {
          // also remove separating space after style-name
          endIndex++;
        }
        buffer.append(' ');
        buffer.append(this.styles.substring(0, startIndex));
        buffer.append(this.styles.substring(endIndex));
      } else {
        // the primaryStyle is a new style - we have to replace the current primary style.
        int index = this.styles.indexOf(' ');
        if (index >= 0) {
          buffer.append(' ');
          buffer.append(this.styles.substring(index + 1));
        } else {
          // nothing to do...
        }
      }
      this.styles = buffer.toString();
    }
    updateStyles();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean hasStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    return (getIndexOfStyle(style) >= 0);
  }

  /**
   * @see #hasStyle(String)
   * 
   * @param style is the {@link #hasStyle(String) style to check}.
   * @return the start-index of the given <code>style</code> in {@link #getStyles() styles} or <code>-1</code>
   *         if NOT present.
   */
  private int getIndexOfStyle(String style) {

    int length = style.length();
    int index = this.styles.indexOf(style);
    while (index >= 0) {
      boolean validStart = true;
      int end = index + length;
      if (index > 0) {
        char c = this.styles.charAt(index - 1);
        if (c != ' ') {
          validStart = false;
        }
      }
      if (validStart) {
        if ((end == this.styles.length()) || (this.styles.charAt(end) == ' ')) {
          return index;
        }
      }
      index = this.styles.indexOf(style, end);
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    assert (style.matches(STYLE_PATTERN_SINGLE));
    if (!hasStyle(style)) {
      if (this.styles == null) {
        this.styles = style;
      } else {
        this.styles = this.styles + " " + style;
      }
      updateStyles();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    assert (!style.isEmpty());
    assert (!style.contains(" "));
    int startIndex = getIndexOfStyle(style);
    if (startIndex >= 0) {
      int endIndex = startIndex + style.length();
      if (endIndex < this.styles.length()) {
        // also remove separating space after style-name
        endIndex++;
      }
      this.styles = this.styles.substring(0, startIndex) + this.styles.substring(endIndex);
      updateStyles();
      return true;
    }
    return false;
  }

  /**
   * This method updates the styles in the view.
   */
  protected final void updateStyles() {

    if (hasWidgetAdapter()) {
      this.widgetAdapter.setStyles(this.styles);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * This is the most general implementation for a {@link UiWidget} that has no value. It always returns
   * <code>false</code>. This method has to be overridden by more specific widgets.
   */
  @Override
  public final boolean isModified() {

    if (isModifiedLocal()) {
      return true;
    }
    return isModifiedRecursive();
  }

  /**
   * @return {@link #isModified()} for this {@link UiWidget} itself (without {@link #isModifiedRecursive()
   *         recursion}).
   */
  protected boolean isModifiedLocal() {

    return false;
  }

  /**
   * @return <code>true</code> if this is a {@link net.sf.mmm.client.ui.api.widget.UiWidgetComposite
   *         composite widget} and one of its children is {@link #isModified() modified}.
   */
  boolean isModifiedRecursive() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean validate(ValidationState state) {

    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }
    validateLocal(validationState);
    validateRecursive(validationState);
    return state.isValid();
  }

  /**
   * This method performs the local validation of this widget excluding potential child widgets.
   * 
   * @param state is the {@link ValidationState}.
   */
  protected void validateLocal(ValidationState state) {

    // nothing to do...
  }

  /**
   * This method performs the recursive validation of potential children of this widget excluding the
   * validation of this widget itself. A legal implementation of a composite widget needs to call
   * {@link #validate(ValidationState)} on all child widgets.
   * 
   * @param state is the {@link ValidationState}.
   */
  void validateRecursive(ValidationState state) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[");
      buffer.append(this.id);
      buffer.append("]");
    }
    if (!this.visible) {
      buffer.append("[hidden]");
    }
    if (!this.enabled) {
      buffer.append("[disabled]");
    }
    return buffer.toString();
  }

}
