/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.heuristic.selector.value.chained;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A subList out of a single chain.
 * <p/>
 * Never includes an anchor.
 */
public class SubChain implements Serializable {

    private final List<Object> entityList;

    public SubChain(List<Object> entityList) {
        this.entityList = entityList;
    }

    public List<Object> getEntityList() {
        return entityList;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    public Object getFirstEntity() {
        if (entityList.isEmpty()) {
            return null;
        }
        return entityList.get(0);
    }

    public Object getLastEntity() {
        if (entityList.isEmpty()) {
            return null;
        }
        return entityList.get(entityList.size() - 1);
    }

    public int getSize() {
        return entityList.size();
    }

    public SubChain reverse() {
        List<Object> reversedEntityList = new ArrayList<Object>(entityList);
        Collections.reverse(reversedEntityList);
        return new SubChain(reversedEntityList);
    }

    public SubChain subChain(int fromIndex, int toIndex) {
        return new SubChain(entityList.subList(fromIndex, toIndex));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof SubChain) {
            SubChain other = (SubChain) o;
            return entityList.equals(other.entityList);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return entityList.hashCode();
    }

    @Override
    public String toString() {
        return entityList.toString();
    }

}
