/*
 * Copyright 2023 Michael Bishop
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

package io.github.mmbishop.gwttest.core;

import io.github.mmbishop.gwttest.model.TestPhase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validates test phase transitions.
 */
public class TestPhaseValidator {

    private final Map<TestPhase, List<TestPhase>> testPhaseTransitionMap;

    public TestPhaseValidator() {
        testPhaseTransitionMap = new HashMap<>();
        testPhaseTransitionMap.put(TestPhase.CONSTRUCTED, Collections.emptyList());
        testPhaseTransitionMap.put(TestPhase.GIVEN, List.of(TestPhase.CONSTRUCTED));
        testPhaseTransitionMap.put(TestPhase.WHEN, List.of(TestPhase.CONSTRUCTED, TestPhase.GIVEN, TestPhase.THEN));
        testPhaseTransitionMap.put(TestPhase.THEN, List.of(TestPhase.CONSTRUCTED, TestPhase.GIVEN, TestPhase.WHEN));
    }

    /**
     * Validates a phase transition in a GwtTest.
     * @param currentPhase the current phase the test is in
     * @param nextPhase the next phase the test will be in based on which method (.given, .when or .then) is being called
     * @throws MalformedTestException the phase transition is invalid
     */
    void validatePhaseTransition(TestPhase currentPhase, TestPhase nextPhase) {
        if (currentPhase == null) {
            return;
        }
        var validCurrentPhases = testPhaseTransitionMap.get(nextPhase);
        if (!validCurrentPhases.contains(currentPhase)) {
            throw new MalformedTestException("Invalid test phase transition from " + currentPhase + " to " + nextPhase);
        }
    }

    /**
     * Validates a self transition in a GwtTest. This occurs when the .and method is called.
     * @param currentPhase the current phase the test is in
     * @throws MalformedTestException there is no current phase, meaning that .and was called before .given, .when or .then
     */
    void validateSelfTransition(TestPhase currentPhase) {
        if (currentPhase == null || currentPhase.equals(TestPhase.CONSTRUCTED)) {
            throw new MalformedTestException(".and called before .given, .when and .then");
        }
    }

}
