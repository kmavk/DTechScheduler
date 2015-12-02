/*
 * Copyright 2010 JBoss Inc
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

package org.optaplanner.benchmark.impl.ranking;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.optaplanner.benchmark.impl.result.SolverBenchmarkResult;
import org.optaplanner.core.api.score.Score;

/**
 * This ranking {@link Comparator} orders a {@link SolverBenchmarkResult} by its worst {@link Score}.
 * It minimizes the worst case scenario.
 */
public class WorstScoreSolverRankingComparator implements Comparator<SolverBenchmarkResult>, Serializable {

    private final ResilientScoreComparator resilientScoreComparator
            = new ResilientScoreComparator();

    public int compare(SolverBenchmarkResult a, SolverBenchmarkResult b) {
        List<Score> aScoreList = a.getScoreList();
        Collections.sort(aScoreList); // Worst scores become first in the list
        List<Score> bScoreList = b.getScoreList();
        Collections.sort(bScoreList); // Worst scores become first in the list
        int aSize = aScoreList.size();
        int bSize = bScoreList.size();
        for (int i = 0; i < aSize && i < bSize; i++) {
            int comparison = resilientScoreComparator.compare(aScoreList.get(i), bScoreList.get(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        return aSize == bSize ? 0 : aSize < bSize ? -1 : 1;
    }

}
