/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.custom.SashForm}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncSashFormAccess extends AbstractSyncCompositeAccess {

  /**
   * operation to set the {@link SashForm#setWeights(int[]) weight} of the
   * sash-form.
   */
  private static final String OPERATION_SET_WEIGHTS = "setWeights";

  /**
   * operation to set the {@link SashForm#setOrientation(int) orientation} of
   * the sash-form.
   */
  private static final String OPERATION_SET_ORIENTATION = "setOrientation";

  /** the sash-form */
  private SashForm sashForm;

  /** the weights to set */
  private int[] weights;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the sash-form.
   */
  public SyncSashFormAccess(UIFactorySwt uiFactory, int swtStyle) {

    this(uiFactory, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the sash-form.
   * @param swtSashForm
   *        is the widget to access.
   */
  public SyncSashFormAccess(UIFactorySwt uiFactory, int swtStyle, SashForm swtSashForm) {

    super(uiFactory, swtStyle);
    this.sashForm = swtSashForm;
    this.weights = new int[2];
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess#getSwtObject()
   * 
   */
  @Override
  public Composite getSwtObject() {

    return this.sashForm;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#performSynchron(java.lang.String)
   * 
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_WEIGHTS) {
      this.sashForm.setWeights(this.weights);
    } else if (operation == OPERATION_SET_ORIENTATION) {
      int style;
      if (hasStyle(SWT.VERTICAL)) {
        style = SWT.VERTICAL;
      } else {
        style = SWT.HORIZONTAL;
      }
      this.sashForm.setOrientation(style);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#createSynchron()
   * 
   */
  @Override
  protected void createSynchron() {

    this.sashForm = new SashForm(getParent(), getStyle());
    if ((this.weights[0] + this.weights[1]) == 100) {
      this.sashForm.setWeights(this.weights);
    }
    super.createSynchron();
  }

  /**
   * 
   * @param weight
   */
  public void setWeights(int weight) {

    assert (checkReady());
    this.weights[0] = weight;
    this.weights[1] = 100 - weight;
    invoke(OPERATION_SET_WEIGHTS);
  }

  /**
   * This method sets the {@link SashForm#setOrientation(int) orientation} of
   * the sash-form.
   * 
   * @param sashOrientation -
   *        {@link org.eclipse.swt.SWT#HORIZONTAL} for horizontal or
   *        {@link org.eclipse.swt.SWT#VERTICAL} for vertical orientation.
   */
  public void setOrientation(int sashOrientation) {

    if ((sashOrientation == SWT.HORIZONTAL) || (sashOrientation == SWT.VERTICAL)) {
      if (!hasStyle(sashOrientation)) {
        assert (checkReady());
        invertFlag(SWT.HORIZONTAL | SWT.VERTICAL);
        invoke(OPERATION_SET_ORIENTATION);
      }
    } else {
      throw new IllegalArgumentException("Illegal orientation: " + sashOrientation);
    }
  }

}
