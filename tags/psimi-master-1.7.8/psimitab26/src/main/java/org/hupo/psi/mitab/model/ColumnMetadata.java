/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.mitab.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ColumnMetadata {

    private String key;
    private String name;

    private String subKey;
    private boolean onlyValues;
    private String defaultValue;
    private String readDefaultType;
    private String writeDefaultType;

    private List<ColumnMetadata> synonymColumns;

    public ColumnMetadata(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public ColumnMetadata(String key, String name, String subKey) {
        this.key = key;
        this.name = name;
        this.subKey = subKey;
    }

    public ColumnMetadata(String key, String name, String subKey, boolean onlyValues) {
        this.key = key;
        this.name = name;
        this.subKey = subKey;
        this.onlyValues = onlyValues;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public boolean isOnlyValues() {
        return onlyValues;
    }

    public void setOnlyValues(boolean onlyValues) {
        this.onlyValues = onlyValues;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getReadDefaultType() {
        return readDefaultType;
    }

    public void setReadDefaultType(String readDefaultType) {
        this.readDefaultType = readDefaultType;
    }

    public String getWriteDefaultType() {
        return writeDefaultType;
    }

    public void setWriteDefaultType(String writeDefaultType) {
        this.writeDefaultType = writeDefaultType;
    }

    public List<ColumnMetadata> getSynonymColumns() {
        if (synonymColumns == null) {
            synonymColumns = new ArrayList<ColumnMetadata>();
        }
        return synonymColumns;
    }

    public void setSynonymColumns(List<ColumnMetadata> synonymColumns) {
        this.synonymColumns = synonymColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnMetadata that = (ColumnMetadata) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (subKey != null ? !subKey.equals(that.subKey) : that.subKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (subKey != null ? subKey.hashCode() : 0);
        return result;
    }
}
