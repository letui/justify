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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.api.Evaluator;
import org.leadpony.justify.api.ProblemDispatcher;

/**
 * Evaluator for "allOf" boolean logic.
 * 
 * @author leadpony
 */
public class SimpleConjunctiveEvaluator extends AbstractLogicalEvaluator 
    implements Iterable<Evaluator> {

    private final List<Evaluator> operands = new ArrayList<>();
    
    SimpleConjunctiveEvaluator() {
    }
    
    @Override
    public Result evaluate(Event event, JsonParser parser, int depth, ProblemDispatcher dispatcher) {
        int evaluationsAsFalse = 0;
        for (Evaluator operand : operands) {
            if (operand.evaluate(event, parser, depth, dispatcher) == Result.FALSE) {
                evaluationsAsFalse++;
            }
        }
        return (evaluationsAsFalse == 0) ? Result.TRUE : Result.FALSE;
    }

    @Override
    public void append(Evaluator evaluator) {
        if (evaluator.isAlwaysTrue()) {
            return;
        }
        this.operands.add(evaluator);
    }

    @Override
    public Iterator<Evaluator> iterator() {
        return operands.iterator();
    }
}
