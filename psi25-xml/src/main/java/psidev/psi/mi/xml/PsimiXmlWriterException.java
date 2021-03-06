/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml;

/**
 * Specific exception for the PSI-MI XML Writer.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlWriterException extends Exception {
    public PsimiXmlWriterException() {
        super();
    }

    public PsimiXmlWriterException( String message ) {
        super( message );
    }

    public PsimiXmlWriterException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PsimiXmlWriterException( Throwable cause ) {
        super( cause );
    }
}