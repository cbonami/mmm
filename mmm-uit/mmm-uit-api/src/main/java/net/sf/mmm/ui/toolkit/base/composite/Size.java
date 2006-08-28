/* $Id$ */
package net.sf.mmm.ui.toolkit.base.composite;

import net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF;

/**
 * This class is a simple container for the size of a
 * {@link net.sf.mmm.ui.toolkit.api.UIComponentIF component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Size {

    /**
     * The horizontal size of the component.
     */
    public int width;

    /**
     * The vertical size of the component.
     */
    public int height;

    /**
     * The constructor for empty size.
     */
    public Size() {

        this(0, 0);
    }

    /**
     * The constructor.
     * 
     * @param size
     *        is a {@link UIReadSizeIF sized-object} that will be converted to a
     *        {@link Size}.
     */
    public Size(UIReadSizeIF size) {

        this(size.getWidth(), size.getHeight());
    }

    /**
     * The constructor.
     * 
     * @param w
     *        is the {@link #width}
     * @param h
     *        is the {@link #height}
     */
    public Size(int w, int h) {

        super();
        this.width = w;
        this.height = h;
    }

    /**
     * This method swapps {@link #width}/{@link #height}.
     */
    public void swap() {

        int swap = this.height;
        this.height = this.width;
        this.width = swap;
    }

    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return "[w=" + this.width + ",h=" + this.height + "]";
    }

}
