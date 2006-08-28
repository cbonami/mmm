/* $Id: UIDisplay.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.base.UIAbstractDisplay;

/**
 * This class is the implementation of the UIDisplayIF interface using AWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDisplay extends UIAbstractDisplay {

    /** the AWT graphics configuration */
    private GraphicsConfiguration gc;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param uiDevice
     *        is the device the display belongs to.
     * @param graphicConfiguration
     *        is the graphics configuration for the diplay to represent.
     */
    public UIDisplay(UIFactoryIF uiFactory, UIDevice uiDevice,
            GraphicsConfiguration graphicConfiguration) {

        super(uiFactory, uiDevice);
        //GraphicsEnvironment.getLocalGraphicsEnvironment()
        //.getDefaultScreenDevice().getDefaultConfiguration()
        this.gc = graphicConfiguration;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        return this.gc.getBounds().width;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        return this.gc.getBounds().height;
    }

    /**
     * This method gets the graphics configuration. The method is only for
     * internal usage - do NOT access from outside (never leave the API).
     * 
     * @return the AWT graphics configuration object.
     */
    public GraphicsConfiguration getGraphicsConfiguration() {

        return this.gc;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#dispatch()
     * {@inheritDoc}
     */
    public void dispatch() {

        //Sleep some time?        
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#isDispatchThread()
     * {@inheritDoc}
     */
    public boolean isDispatchThread() {

        return EventQueue.isDispatchThread();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#invokeAsynchron(java.lang.Runnable)
     * {@inheritDoc}
     */
    public void invokeAsynchron(Runnable task) {

        EventQueue.invokeLater(task);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractDisplay#doInvokeSynchron(java.lang.Runnable)
     * {@inheritDoc}
     */
    @Override
    protected void doInvokeSynchron(Runnable task) {
    
        try {
            EventQueue.invokeAndWait(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}