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
package org.leadpony.justify.internal.problem;

import java.util.Locale;
import java.util.function.Consumer;

import org.leadpony.justify.api.Problem;

/**
 * @author leadpony
 */
public interface ProblemRenderer {

    ProblemRenderer DEFAULT_RENDERER = new DefaultProblemRenderer(LineFormat.FULL);

    void render(Problem problem, Locale locale, Consumer<String> consumer);

    String render(Problem problem, Locale locale);
}
