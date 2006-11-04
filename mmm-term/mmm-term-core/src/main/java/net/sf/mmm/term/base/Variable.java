/* $Id$ */
package net.sf.mmm.term.base;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This class represents a variable {@link net.sf.mmm.term.api.TermIF term}.
 * The variable simply contains a variable-name and
 * {@link net.sf.mmm.term.api.TermIF#evaluate(Context) evaluates} to the
 * {@link net.sf.mmm.context.api.Context#getValue(String) "environment value"}
 * with that name.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Variable extends AbstractVariable {

    /** uid for serialization */
    private static final long serialVersionUID = -8438644468276039165L;

    /** the name of the variable */
    private final String variableName;

    /**
     * The constructor.
     * 
     * @param name
     *        is the name of the variable.
     */
    public Variable(String name) {

        super();
        this.variableName = name;
    }

    /**
     * This method gets the name of the variable. Parameters are ignored.
     * 
     * @see net.sf.mmm.term.base.AbstractVariable#getVariableName(Context)
     */
    public String getVariableName(Context environment) {

        return this.variableName;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     */
    public String toString() {

        return VARIABLE_START + this.variableName + VARIABLE_END;
    }

    /**
     * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(XmlWriter)
     * 
     */
    public void toXml(XmlWriter xmlWriter) throws XmlException {

        xmlWriter.writeStartElement(XML_TAG_VARIABLE);
        xmlWriter.writeAttribute(XML_ATR_VARIABLE_NAME, this.variableName);
        xmlWriter.writeEndElement(XML_TAG_VARIABLE);
    }

}
