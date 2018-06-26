/*
 * Copyright 2018 the Justify authors.
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

package org.leadpony.justify.internal.assertion;

import javax.json.JsonValue;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

import org.leadpony.justify.core.Evaluator.Reporter;
import org.leadpony.justify.core.Evaluator.Result;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.internal.base.ProblemBuilder;

/**
 * Assertion specified with "const" keyword.
 * 
 * @author leadpony
 */
public class Const extends AbstractEqualityAssertion {

    private final JsonValue expected;
    
    public Const(JsonValue expected, JsonProvider jsonProvider) {
        super(jsonProvider);
        this.expected = expected;
    }
    
    @Override
    public void toJson(JsonGenerator generator) {
        generator.write("const", this.expected);
    }

    @Override
    protected AbstractAssertion createNegatedAssertion() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Result testValue(JsonValue actual, JsonParser parser, Reporter reporter) {
        if (actual.equals(expected)) {
            return Result.TRUE;
        } else {
            Problem p = ProblemBuilder.newBuilder(parser)
                    .withMessage("instance.problem.const")
                    .withParameter("actual", actual)
                    .withParameter("expected", expected)
                    .build();
            reporter.reportProblem(p);
            return Result.FALSE;
        }
    }
}
