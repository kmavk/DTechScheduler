/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.core.impl.domain.variable.custom;

import java.io.Serializable;

import org.optaplanner.core.impl.domain.variable.inverserelation.SingletonInverseVariableSupply;
import org.optaplanner.core.impl.domain.variable.supply.Demand;
import org.optaplanner.core.impl.score.director.InnerScoreDirector;

/**
 * Unlike other {@link Demand}s, a custom demand isn't equalized based on its sources, but based on its target.
 * Therefore a custom shadow variable cannot be reused by build-in systems.
 */
public class CustomShadowVariableDemand implements Demand<SingletonInverseVariableSupply>, Serializable {

    private static final int CLASS_NAME_HASH_CODE = CustomShadowVariableDemand.class.getName().hashCode() * 37;

    private final CustomShadowVariableDescriptor targetShadowVariableDescriptor;

    public CustomShadowVariableDemand(CustomShadowVariableDescriptor targetShadowVariableDescriptor) {
        this.targetShadowVariableDescriptor = targetShadowVariableDescriptor;
    }

    // ************************************************************************
    // Creation method
    // ************************************************************************

    public SingletonInverseVariableSupply createExternalizedSupply(InnerScoreDirector scoreDirector) {
        throw new IllegalArgumentException("A custom shadow variable cannot be externalized.");
    }

    // ************************************************************************
    // Equals/hashCode method
    // ************************************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomShadowVariableDemand)) {
            return false;
        }
        CustomShadowVariableDemand other = (CustomShadowVariableDemand) o;
        return targetShadowVariableDescriptor == other.targetShadowVariableDescriptor;
    }

    @Override
    public int hashCode() {
        return CLASS_NAME_HASH_CODE + targetShadowVariableDescriptor.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
