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

package org.leadpony.justify.internal.keyword.combiner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.internal.keyword.AbstractKeyword;
import org.leadpony.justify.internal.keyword.Evaluatable;
import org.leadpony.justify.internal.keyword.SchemaKeyword;

/**
 * The type for combining subschemas.
 *
 * @author leadpony
 */
public abstract class Combiner extends AbstractKeyword {

    protected Combiner(JsonValue json) {
        super(json);
    }

    protected Combiner(String name, JsonValue json) {
        super(name, json);
    }

    @Override
    public void addToEvaluatables(List<Evaluatable> evaluatables, Map<String, SchemaKeyword> keywords) {
        evaluatables.add(this);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This method must be overridden.</p>
     */
    public JsonSchema getSubschema(Iterator<String> jsonPointer) {
        throw new UnsupportedOperationException();
    }
}
