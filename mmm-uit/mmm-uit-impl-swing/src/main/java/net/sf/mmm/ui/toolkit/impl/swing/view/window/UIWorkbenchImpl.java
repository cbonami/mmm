/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIWorkbenchImpl extends UIFrameImpl implements UiWorkbench {

  /** the default layer used to add internal frames */
  private static final Integer DEFAULT_LAYER = new Integer(1);

  /** the workbench pane */
  private final JDesktopPane workbench;

  // private final List<UiInternalFrame> frameList;

  /** @see #setComposite(UiComposite) */
  private JPanel contentPane;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizeable}.
   */
  public UIWorkbenchImpl(UIFactorySwing uiFactory, String title, boolean resizeable) {

    super(uiFactory, null, title, resizeable);
    // UIManager.put("swing.boldMetal", Boolean.FALSE);
    // JFrame.setDefaultLookAndFeelDecorated(true);
    // Toolkit.getDefaultToolkit().setDynamicLayout(true);
    this.workbench = new JDesktopPane();
    getNativeWindow().setContentPane(this.workbench);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return UiWorkbench.TYPE;
  }

  /**
   * This method gets the desktop panel of this workbench.
   * 
   * @return the desktop panel.
   */
  public JDesktopPane getDesktopPanel() {

    return this.workbench;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    if (this.contentPane == null) {
      this.contentPane = new JPanel(new GridLayout(1, 1));
      this.contentPane.setSize(getWidth(), getHeight());
      this.workbench.add(this.contentPane);
      this.workbench.addComponentListener(new ResizeListener());
    } else if (this.contentPane.getComponentCount() > 0) {
      this.contentPane.removeAll();
    }
    JComponent jComponent = ((AbstractUiElement) newComposite).getSwingComponent();
    this.contentPane.add(jComponent);
    // TODO...
    // throw new IllegalArgumentException("This method is not applicable for " +
    // UIWorkbenchImpl.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame createFrame(String title, boolean resizeable) {

    UiInternalFrame internalFrame = new UiInternalFrame((UIFactorySwing) getFactory(), this, title,
        resizeable);
    this.workbench.add(internalFrame.getSwingInternalFrame(), DEFAULT_LAYER);
    getFactory().addWindow(internalFrame);
    return internalFrame;
  }

  /**
   * This listener for resizing the content pane.
   */
  private final class ResizeListener implements ComponentListener {

    /**
     * {@inheritDoc}
     */
    public void componentShown(ComponentEvent e) {

    }

    /**
     * {@inheritDoc}
     */
    public void componentResized(ComponentEvent e) {

      UIWorkbenchImpl.this.contentPane.setSize(getWidth(), getHeight());
    }

    /**
     * {@inheritDoc}
     */
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * {@inheritDoc}
     */
    public void componentHidden(ComponentEvent e) {

    }
  }

}
