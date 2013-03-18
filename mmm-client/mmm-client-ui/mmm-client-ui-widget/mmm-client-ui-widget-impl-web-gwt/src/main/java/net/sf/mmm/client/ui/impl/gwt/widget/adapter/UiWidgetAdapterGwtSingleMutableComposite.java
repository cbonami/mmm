/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import java.util.Iterator;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;
import net.sf.mmm.util.collection.base.SingleElementIterator;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterSingleMutableComposite} using GWT.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <CHILD> is the generic type of the {@link #setChild(UiWidget) child}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtSingleMutableComposite<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetAdapterGwtWidget<WIDGET> implements UiWidgetAdapterSingleMutableComposite<CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtSingleMutableComposite() {

    super();
  }

  /**
   * This inner class is a custom {@link Widget} that represents a {@link Panel} owing a single child.
   */
  public abstract static class SingleCompositePanel extends Panel {

    /** @see #setChild(Widget) */
    private Widget childWidget;

    /**
     * The constructor.
     */
    public SingleCompositePanel() {

      super();
    }

    /**
     * @param child is the Widget to set as child. A potential previous child will be removed.
     */
    public void setChild(Widget child) {

      if (this.childWidget != null) {
        remove(this.childWidget);
      }
      add(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Widget> iterator() {

      return new SingleElementIterator<Widget>(this.childWidget);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Widget child) {

      if (this.childWidget == null) {
        getElement().appendChild(child.getElement());
        this.childWidget = child;
        adopt(child);
      } else {
        throw new IllegalStateException("Multiple children not supported!");
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Widget child) {

      if (child == null) {
        return false;
      }
      if (this.childWidget == child) {
        orphan(child);
        getElement().removeChild(child.getElement());
        this.childWidget = null;
        return true;
      }
      return false;
    }

  }
}
