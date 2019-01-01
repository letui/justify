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
package org.leadpony.justify.internal.keyword.assertion.content;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.api.Evaluator;
import org.leadpony.justify.api.InstanceType;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ProblemDispatcher;
import org.leadpony.justify.internal.keyword.Keyword;
import org.leadpony.justify.internal.keyword.assertion.AbstractAssertion;
import org.leadpony.justify.spi.ContentEncodingScheme;
import org.leadpony.justify.spi.ContentMimeType;

/**
 * Content keyword representing "contentMediaType".
 *
 * @author leadpony
 */
public class ContentMediaType extends AbstractAssertion implements Evaluator {

    private final ContentMimeType mimeType;
    private final Map<String, String> parameters;
    private ContentEncodingScheme encodingScheme;

    /**
     * Constructs this media type.
     *
     * @param mimeType the type/subtype part of this media type.
     * @param parameters additional parameters of this media type.
     */
    public ContentMediaType(ContentMimeType mimeType, Map<String, String> parameters) {
        this.mimeType = mimeType;
        this.parameters = parameters;
    }

    @Override
    public String name() {
        return "contentMediaType";
    }

    @Override
    public boolean supportsType(InstanceType type) {
        return type == InstanceType.STRING;
    }

    @Override
    public Set<InstanceType> getSupportedTypes() {
        return EnumSet.of(InstanceType.STRING);
    }

    @Override
    protected Evaluator doCreateEvaluator(InstanceType type, JsonBuilderFactory builderFactory) {
        return this;
    }

    @Override
    protected Evaluator doCreateNegatedEvaluator(InstanceType type, JsonBuilderFactory builderFactory) {
        return this::evaluateNegated;
    }

    @Override
    public void addToJson(JsonObjectBuilder builder, JsonBuilderFactory builderFactory) {
        builder.add(name(), value());
    }

    @Override
    public void addToEvaluatables(List<Keyword> evaluatables, Map<String, Keyword> keywords) {
        if (keywords.containsKey("contentEncoding")) {
            Keyword keyword = keywords.get("contentEncoding");
            if (keyword instanceof ContentEncoding) {
                this.encodingScheme = ((ContentEncoding) keyword).scheme();
            } else {
                // Unknown encoding scheme
                return;
            }
        }
        evaluatables.add(this);
    }

    @Override
    public Result evaluate(Event event, JsonParser parser, int depth, ProblemDispatcher dispatcher) {
        String value = ((JsonString) parser.getValue()).getString();
        if (testValue(value, true)) {
            return Result.TRUE;
        } else {
            dispatcher.dispatchProblem(buildProblem(parser, "instance.problem.contentMediaType"));
            return Result.FALSE;
        }
    }

    public Result evaluateNegated(Event event, JsonParser parser, int depth, ProblemDispatcher dispatcher) {
        String value = ((JsonString) parser.getValue()).getString();
        if (testValue(value, false)) {
            dispatcher.dispatchProblem(buildProblem(parser, "instance.problem.not.contentMediaType"));
            return Result.FALSE;
        } else {
            return Result.TRUE;
        }
    }

    private boolean testValue(String value, boolean defaultResult) {
        if (encodingScheme != null) {
            if (encodingScheme.canDecode(value)) {
                byte[] decoded = encodingScheme.decode(value);
                return mimeType.test(decoded, parameters);
            } else {
                return defaultResult;
            }
        } else {
            return mimeType.test(value);
        }
    }

    /**
     * Returns the value of this media type.
     *
     * @return the value of this media type.
     */
    String value() {
        return mimeType.toString();
    }

    private Problem buildProblem(JsonParser parser, String messageKey) {
        return createProblemBuilder(parser).withMessage(messageKey).withParameter("type", value()).build();
    }
}
