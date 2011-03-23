package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.FieldParser;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public abstract class AbstractColumnDefinition extends AbstractDefined implements ColumnDefinition {


    private int position;
    private String key;
    private boolean allowsEmpty;
    private String emptyValue;
    private FieldParser fieldParser;
    private FieldFormatter fieldFormatter;
    private String fieldSeparator;
    private String fieldDelimiter;

    public AbstractColumnDefinition(){
        this.allowsEmpty = true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition( int position ) {
        this.position = position;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public boolean isAllowsEmpty() {
        return allowsEmpty;
    }

    public void setAllowsEmpty( boolean allowsEmpty ) {
        this.allowsEmpty = allowsEmpty;
    }

    public String getEmptyValue() {
        return emptyValue;
    }

    public void setEmptyValue( String emptyValue ) {
        this.emptyValue = emptyValue;
    }

    public FieldParser getFieldParser() {
        return fieldParser;
    }

    public void setFieldParser(FieldParser fieldParser) {
        this.fieldParser = fieldParser;
    }

    public FieldFormatter getFieldFormatter() {
        return fieldFormatter;
    }

    public void setFieldFormatter(FieldFormatter fieldFormatter) {
        this.fieldFormatter = fieldFormatter;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator( String fieldSeparator ) {
        this.fieldSeparator = fieldSeparator;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public boolean hasFieldDelimiter() {
        return fieldDelimiter != null;
    }

    public void setFieldDelimiter( String fieldDelimiter ) {
        this.fieldDelimiter = fieldDelimiter;
    }
}
