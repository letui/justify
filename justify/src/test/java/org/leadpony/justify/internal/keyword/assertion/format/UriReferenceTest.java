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

package org.leadpony.justify.internal.keyword.assertion.format;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * A test class for {@link UriReference}.
 *
 * @author leadpony
 */
public class UriReferenceTest {

    // System under test
    private static UriReference sut;

    private static int index;

    @BeforeAll
    public static void setUpOnce() {
        sut = new UriReference(true);
    }

    public static Stream<UriFixture> uris() {
        return UriFixture.load("uri.json")
                .filter(UriFixture::isValid);
    }

    public static Stream<UriFixture> uriRefs() {
        List<String> files = Arrays.asList(
                "uri-ref.json",
                "/com/sporkmonger/addressable/uri.json");
        return files.stream().flatMap(UriFixture::load);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("uris")
    public void testUri(UriFixture fixture) {
        Assumptions.assumeTrue(++index >= 0);
        boolean valid = sut.test(fixture.value());
        assertThat(valid).isEqualTo(fixture.isValid());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("uriRefs")
    public void testUriReference(UriFixture fixture) {
        Assumptions.assumeTrue(++index >= 0);
        boolean valid = sut.test(fixture.value());
        assertThat(valid).isEqualTo(fixture.isValid());
    }
}
