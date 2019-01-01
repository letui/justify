/*
 * Copyright 2018-2019 the Justify authors.
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

package org.leadpony.justify.internal.evaluator;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.stream.JsonParser;

import org.leadpony.justify.api.ProblemDispatcher;
import org.leadpony.justify.internal.base.ProblemBuilder;
import org.leadpony.justify.internal.base.ProblemList;

/**
 * Skeletal implementation for {@link ExclusiveEvaluator} and {@link SimpleExclusiveEvaluator}.
 * @author leadpony
 */
abstract class AbstractExclusiveEvaluator extends AbstractLogicalEvaluator {

    protected void dispatchProblems(JsonParser parser, ProblemDispatcher dispatcher, List<ProblemList> problemLists) {
        List<ProblemList> filteredLists = problemLists.stream()
                .filter(ProblemList::isResolvable)
                .collect(Collectors.toList());
        if (filteredLists.isEmpty()) {
            filteredLists = problemLists;
        }
        if (filteredLists.size() == 1) {
            dispatcher.dispatchAllProblems(filteredLists.get(0));
        } else {
            ProblemBuilder builder = createProblemBuilder(parser)
                    .withMessage("instance.problem.oneOf.few")
                    .withBranches(filteredLists);
            dispatcher.dispatchProblem(builder.build());
        }
    }

    protected void dispatchNegatedProblems(JsonParser parser, ProblemDispatcher dispatcher, List<ProblemList> problemLists) {
        ProblemBuilder builder = createProblemBuilder(parser)
                .withMessage("instance.problem.oneOf.many")
                .withBranches(problemLists);
        dispatcher.dispatchProblem(builder.build());
    }
}
