/* $Id: UIAbstractDisplay.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base;

import java.util.concurrent.Callable;

import net.sf.mmm.ui.toolkit.api.UIDeviceIF;
import net.sf.mmm.ui.toolkit.api.UIDisplayIF;
import net.sf.mmm.ui.toolkit.api.UIFactoryIF;

/**
 * This is the abstract base implementation of the UIDisplayIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractDisplay extends UIAbstractObject implements UIDisplayIF {

    /** the UI device this display belongs to */
    private UIDeviceIF device;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UI factory instance.
     * @param uiDevice
     *        is the device the display belongs to.
     */
    public UIAbstractDisplay(UIFactoryIF uiFactory, UIDeviceIF uiDevice) {

        super(uiFactory);
        this.device = uiDevice;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    public String toString() {

        return getDevice().toString() + "[" + getWidth() + "*" + getHeight() + "]";
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#getDevice()
     * {@inheritDoc}
     */
    public UIDeviceIF getDevice() {

        return this.device;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#invokeSynchron(java.util.concurrent.Callable)
     * {@inheritDoc}
     */
    public <T> T invokeSynchron(Callable<T> task) throws Exception {

        if (isDispatchThread()) {
            return task.call();
        } else {
            CallableRunner<T> runner = new CallableRunner<T>(task);
            doInvokeSynchron(runner);
            return runner.getResult();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#invokeSynchron(java.lang.Runnable)
     * {@inheritDoc}
     */
    public void invokeSynchron(Runnable task) {

        if (isDispatchThread()) {
            task.run();
        } else {
            doInvokeSynchron(task);
        }
    }

    /**
     * This method handles the {@link #invokeSynchron(Runnable)} if called from
     * another thread.
     * 
     * @param task
     */
    protected abstract void doInvokeSynchron(Runnable task);

}
