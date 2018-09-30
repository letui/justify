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

/**
 * Format attribute representing "uri-reference" attribute.
 * 
 * @author leadpony
 * 
 * @see <a href="https://tools.ietf.org/html/rfc3986">
 * "Uniform Resource Identifier (URI): Generic Syntax", STD 66, RFC 3986</a>
 */
class UriReference implements StringFormatAttribute {

    private final boolean verbose;
    
    UriReference() {
        this(false);
    }
    
    UriReference(boolean verbose) {
        this.verbose = verbose;
    }
    
    @Override
    public String name() {
        return "uri-reference";
    }

    @Override
    public boolean test(String value) {
        UriReferenceMatcher m = verbose ?
                new VerboseUriReferenceMatcher(value) : new UriReferenceMatcher(value);
        return m.matches();
    }
}
