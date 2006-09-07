/* $Id$ */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValueIF;


/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link ObjectValue}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ImmutableObjectValueTest extends AbstractGenericValueTest {

    /**
     * The constructor.
     *
     */
    public ImmutableObjectValueTest() {

        super();
    }
    
    /**
     * @see net.sf.mmm.value.impl.AbstractGenericValueTest#convert(java.lang.Object)
     * {@inheritDoc}
     */
    @Override
    protected GenericValueIF convert(Object plainValue) {

        return new ObjectValue(plainValue);
    }
    
    @Test
    public void testConversion() {
        int i = 42;
        String s = Integer.toString(i);
        GenericValueIF value = convert(s);
        assertEquals(s, value.getString());
        assertEquals(i, value.getInteger());
        assertEquals(i, value.getLong());
        assertEquals(i, value.getDouble(), 0);
        assertEquals(i, value.getNumber().doubleValue(), 0);
        assertEquals(i, value.getValue(Float.class).doubleValue(), 0);
        assertEquals(i, value.getValue(Byte.class).intValue());
        assertEquals(i, value.getValue(Short.class).intValue());
        
        boolean b = true;
        s = Boolean.toString(b);
        value = convert(s);
        assertEquals(s, value.getString());
        assertEquals(b, value.getBoolean());
    }
    
}
