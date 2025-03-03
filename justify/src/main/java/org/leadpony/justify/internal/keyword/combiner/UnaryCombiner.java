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
import java.util.stream.Stream;

import org.leadpony.justify.api.JsonSchema;

/**
 * Combiner operating on single subschema.
 *
 * @author leadpony
 */
abstract class UnaryCombiner extends Combiner {

    private final JsonSchema subschema;

    protected UnaryCombiner(JsonSchema subschema) {
        super(subschema.toJson());
        this.subschema = subschema;
    }

    JsonSchema getSubschema() {
        return subschema;
    }

    @Override
    public boolean hasSubschemas() {
        return true;
    }

    @Override
    public Stream<JsonSchema> getSubschemas() {
        return Stream.of(subschema);
    }

    @Override
    public JsonSchema getSubschema(Iterator<String> jsonPointer) {
        return subschema;
    }
}
