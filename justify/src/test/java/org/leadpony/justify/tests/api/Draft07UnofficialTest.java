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
package org.leadpony.justify.tests.api;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.leadpony.justify.api.SpecVersion;
import org.leadpony.justify.internal.annotation.Spec;

/**
 * @author leadpony
 */
@Spec(SpecVersion.DRAFT_07)
public class Draft07UnofficialTest extends AbstractOfficialTest {

    private static final String[] MANDATORY = {
            "/org/leadpony/justify/tests/api/instance/additionalProperties.json",
            "/org/leadpony/justify/tests/api/instance/if-then-else.json",
    };

    public static Stream<Fixture> mandatory() {
        return generateFixtures(MANDATORY);
    }

    @ParameterizedTest
    @MethodSource("mandatory")
    public void testMandatory(Fixture fixture) {
        test(fixture);
    }
}
