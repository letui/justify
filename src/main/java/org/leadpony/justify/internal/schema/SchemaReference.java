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

package org.leadpony.justify.internal.schema;

import static org.leadpony.justify.internal.base.Arguments.requireNonNull;

import java.net.URI;
import java.util.Map;
import java.util.function.Consumer;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.leadpony.justify.core.Evaluator;
import org.leadpony.justify.core.InstanceType;
import org.leadpony.justify.core.JsonSchema;
import org.leadpony.justify.core.Problem;
import org.leadpony.justify.internal.base.ProblemBuilderFactory;
import org.leadpony.justify.internal.keyword.Keyword;

/**
 * Schema reference containing  "$ref" keyword.
 * 
 * @author leadpony
 */
public class SchemaReference extends AbstractJsonSchema {

    private URI ref;
    @SuppressWarnings("unused")
    private final URI originalRef;
    private JsonSchema referencedSchema;

    /**
     * Constructs this schema reference.
     * 
     * @param ref the URI of the referenced schema.
     * @param keywordMap the keywords contained in this schema.
     * @param builderFactory the builder of JSON arrays and objects.
     */
    public SchemaReference(URI ref, Map<String, Keyword> keywordMap, 
            JsonBuilderFactory builderFactory) {
        super(keywordMap, builderFactory);
        this.ref = this.originalRef = ref;
        this.referencedSchema = new NonexistentSchema();
    }
    
    /**
     * Returns the URI of the referenced schema.
     * 
     * @return the URI of the referenced schema.
     */
    public URI getRef() {
        return ref;
    }
    
    /**
     * Assigns the URI of the referenced schema.
     * 
     * @param ref the URI of the referenced schema, cannot be {@code null}.
     */
    public void setRef(URI ref) {
        requireNonNull(ref, "ref");
        this.ref = ref;
    }
    
    /**
     * Assigns the referenced schema.
     * 
     * @param schema the referenced schema, cannot be {@code null}.
     */
    public void setReferencedSchema(JsonSchema schema) {
        requireNonNull(schema, "schema");
        this.referencedSchema = schema;
    }

    @Override
    public Evaluator evaluator(InstanceType type, EvaluatorFactory factory, boolean affirmative) {
        return referencedSchema.evaluator(type, factory, affirmative);
    }

    @Override
    protected void addToJson(JsonObjectBuilder builder) {
        builder.add("$ref", this.ref.toString());
        super.addToJson(builder);
    }
    
    /**
     * Nonexistent JSON Schema.
     * 
     * @author leadpony
     */
    private class NonexistentSchema implements JsonSchema, Evaluator {

        @Override
        public Evaluator evaluator(InstanceType type, EvaluatorFactory evaluatorFactory, boolean affirmative) {
            return this;
        }

        @Override
        public JsonValue toJson() {
            return JsonValue.FALSE;
        }

        @Override
        public Result evaluate(Event event, JsonParser parser, int depth, Consumer<Problem> reporter) {
            Problem p = ProblemBuilderFactory.DEFAULT.createProblemBuilder(parser)
                    .withKeyword("$ref")
                    .withMessage("schema.problem.dereference")
                    .withParameter("ref", getRef())
                    .build();
            reporter.accept(p);
            return Result.FALSE;
        }
    }
}
