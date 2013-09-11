/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

/**
 * Simple description of confidence score.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class ConfidenceImpl implements Confidence, psidev.psi.mi.jami.model.Confidence, FileSourceContext {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 7009701156529411485L;

    private CvTerm type;
    private String value;
    private CvTerm unit;

    private MitabSourceLocator locator;

    //////////////////////
    // Constructor

    public ConfidenceImpl() {
        this.type = new DefaultCvTerm("unknown");
        this.value = "unknown";
    }

    public ConfidenceImpl( String type, String value ) {
        this.type = new DefaultCvTerm(type != null ? type : "unknown");
        this.value = value != null ? value : "unknown";
    }

    public ConfidenceImpl( String type, String value, String text ) {
        this(type, value);
        if (text != null){
            this.unit = new DefaultCvTerm(text);
        }
    }

    //////////////////////
    // Getters and Setters

    public MitabSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(MitabSourceLocator locator) {
        this.locator = locator;
    }

    public String getConfidenceType() {
        return type.getShortName();
    }

    public void setType(String type) {
        if (type == null){
            type = "unknown";
        }
        this.type = new DefaultCvTerm(type);
    }

    public CvTerm getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public CvTerm getUnit() {
        return unit;
    }

    public void setValue( String value ) {
        if ( value == null ) {
            throw new IllegalArgumentException( "You must give a non null value." );
        }
        this.value = value;
    }

    public String getText() {
        return unit != null ? unit.getShortName() : null;
    }

    public void setText( String text ) {
        if ( text != null ) {
            // ignore empty string
            text = text.trim();
            if ( text.length() == 0 ) {
                text = null;
            }
            this.unit = new DefaultCvTerm(text);
        }
        else {
            this.unit = null;
        }
    }

    /////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ConfidenceImpl" );
        sb.append( "{type='" ).append( type.getShortName() ).append( '\'' );
        sb.append( ", value='" ).append( value ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final ConfidenceImpl that = ( ConfidenceImpl ) o;

        if ( type.getShortName() != null ? !type.getShortName().equals( that.type.getShortName() ) : that.type.getShortName() != null ) {
            return false;
        }
        if ( !value.equals( that.value ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( type.getShortName() != null ? type.getShortName().hashCode() : 0 );
        result = 29 * result + value.hashCode();
        return result;
    }
}