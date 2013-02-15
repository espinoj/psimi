package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for ontology term
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/02/13</pre>
 */

public class DefaultOntologyTerm extends DefaultCvTerm implements OntologyTerm{
    private String definition;

    private Collection<CvTerm> parents;
    private Collection<CvTerm> children;

    public DefaultOntologyTerm(String shortName) {
        super(shortName);
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
    }

    public DefaultOntologyTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
    }

    public DefaultOntologyTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
    }

    public DefaultOntologyTerm(String shortName, String fullName, String miIdentifier, String def){
        this(shortName, fullName, miIdentifier);
        this.definition = def;
    }

    public DefaultOntologyTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
    }

    public DefaultOntologyTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
    }

    public DefaultOntologyTerm(String shortName, String fullName, Xref ontologyId, String def){
        this(shortName, fullName, ontologyId);
        this.definition = def;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String def) {
        this.definition = def;
    }

    public Collection<CvTerm> getParents() {
        return this.parents;
    }

    public Collection<CvTerm> getChildren() {
        return this.children;
    }
}
