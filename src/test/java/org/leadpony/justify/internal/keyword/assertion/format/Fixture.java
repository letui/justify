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

package org.leadpony.justify.internal.keyword.assertion.format;

import java.io.InputStream;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * Test fixture.
 * 
 * @author leadpony
 */
class Fixture {
    
    private final String value;
    private final boolean valid;
    
    static Stream<Fixture> load(String name) {
        InputStream in = Fixture.class.getResourceAsStream(name);
        try (JsonReader reader = Json.createReader(in)) {
            JsonArray array = reader.readArray();
            return array.stream()
                    .map(JsonValue::asJsonObject)
                    .map(Fixture::new);
        }
    }
    
    protected Fixture(JsonObject object) {
        this.value = object.getString("value");
        this.valid = object.getBoolean("valid");
    }
    
    String value() {
        return value;
    }
    
    boolean isValid() {
        return valid;
    }
    
    @Override
    public String toString() {
        return value();
    }
}
