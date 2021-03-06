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
package psidev.psi.mi.tab;

/**
 * PSIMITAB Runtime Exception.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since TODO specify the maven artifact version
 */
public class RuntimePsimiTabException extends RuntimeException {
    public RuntimePsimiTabException() {
        super();
    }

    public RuntimePsimiTabException(String message) {
        super(message);
    }

    public RuntimePsimiTabException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimePsimiTabException(Throwable cause) {
        super(cause);
    }
}
