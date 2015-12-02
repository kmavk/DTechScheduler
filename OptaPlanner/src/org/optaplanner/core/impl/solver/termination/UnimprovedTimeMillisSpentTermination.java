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

package org.optaplanner.core.impl.solver.termination;

import org.optaplanner.core.impl.phase.scope.AbstractPhaseScope;
import org.optaplanner.core.impl.solver.scope.DefaultSolverScope;

public class UnimprovedTimeMillisSpentTermination extends AbstractTermination {

    private final long unimprovedTimeMillisSpentLimit;

    public UnimprovedTimeMillisSpentTermination(long unimprovedTimeMillisSpentLimit) {
        this.unimprovedTimeMillisSpentLimit = unimprovedTimeMillisSpentLimit;
        if (unimprovedTimeMillisSpentLimit <= 0L) {
            throw new IllegalArgumentException("The unimprovedTimeMillisSpentLimit (" + unimprovedTimeMillisSpentLimit
                    + ") cannot be negative.");
        }
    }

    // ************************************************************************
    // Terminated methods
    // ************************************************************************

    public boolean isSolverTerminated(DefaultSolverScope solverScope) {
        long bestSolutionTimeMillis = solverScope.getBestSolutionTimeMillis();
        return isTerminated(bestSolutionTimeMillis);
    }

    public boolean isPhaseTerminated(AbstractPhaseScope phaseScope) {
        long bestSolutionTimeMillis = phaseScope.getPhaseBestSolutionTimeMillis();
        return isTerminated(bestSolutionTimeMillis);
    }

    protected boolean isTerminated(long bestSolutionTimeMillis) {
        long now = System.currentTimeMillis();
        long unimprovedTimeMillisSpent = now - bestSolutionTimeMillis;
        return unimprovedTimeMillisSpent >= unimprovedTimeMillisSpentLimit;
    }

    // ************************************************************************
    // Time gradient methods
    // ************************************************************************

    public double calculateSolverTimeGradient(DefaultSolverScope solverScope) {
        long bestSolutionTimeMillis = solverScope.getBestSolutionTimeMillis();
        return calculateTimeGradient(bestSolutionTimeMillis);
    }

    public double calculatePhaseTimeGradient(AbstractPhaseScope phaseScope) {
        long bestSolutionTimeMillis = phaseScope.getPhaseBestSolutionTimeMillis();
        return calculateTimeGradient(bestSolutionTimeMillis);
    }

    protected double calculateTimeGradient(long bestSolutionTimeMillis) {
        long now = System.currentTimeMillis();
        long unimprovedTimeMillisSpent = now - bestSolutionTimeMillis;
        double timeGradient = ((double) unimprovedTimeMillisSpent) / ((double) unimprovedTimeMillisSpentLimit);
        return Math.min(timeGradient, 1.0);
    }

}
