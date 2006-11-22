/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This is the interface of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbench} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIWorkbenchImpl extends UIFrameImpl implements UIWorkbench {

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   */
  public UIWorkbenchImpl(UIFactorySwt uiFactory) {

    super(uiFactory, null, true);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  @Override
  public String getType() {

    return UIWorkbench.TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#createFrame(java.lang.String,
   *      boolean)
   */
  @Override
  public UIFrameImpl createFrame(String title, boolean resizeable) {

    // TODO
    return super.createFrame(title, resizeable);
  }

}