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

import io.github.mmbishop.gwttest.core.exceptions.ExpectedExceptionNotThrownException;
import io.github.mmbishop.gwttest.core.exceptions.MalformedTestException;
import io.github.mmbishop.gwttest.core.exceptions.TestConstructionException;
import io.github.mmbishop.gwttest.core.exceptions.UnexpectedExceptionCaughtException;
import io.github.mmbishop.gwttest.core.test.ConstructedGwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import io.github.mmbishop.gwttest.model.TestPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * The core class for building Given-When-Then tests.
 * @param <T> a subclass of {@link Context} that contains the fields used in test code.
 */
public class GwtTest<T extends Context> {

    private static Logger logger = LoggerFactory.getLogger(GwtTest.class);

    private TestPhase testPhase;
    private final Class<T> contextClass;
    private T context;

    /**
     * Instantiates a {@code GwtTest} object.
     * @param contextClass the class of the context object used by the test
     */
    public GwtTest(Class<T> contextClass) {
        this.contextClass = contextClass;
    }

    /**
     * Initializes the {@code GwtTest} object
     * @return this {@code GwtTest} object
     * @throws TestConstructionException could not construct the test, most likely due to an inability to
     * instantiate the context class
     */
    public ConstructedGwtTest<T> test() {
        if (testPhase == null) {
            try {
                context = contextClass.getDeclaredConstructor().newInstance();
                context.testName = getCallingMethodName();
                testPhase = TestPhase.CONSTRUCTED;
                return new ConstructedGwtTest<>(context);
            }
            catch (Exception e) {
                throw new TestConstructionException("Can't construct test", e);
            }
        }
        throw new MalformedTestException("Can't call test() more than once.");
    }

    /**
     * Initializes the {@code GwtTest} object
     * @param testName the name of the test
     * @return this {@code GwtTest} object
     * @throws TestConstructionException could not construct the test, most likely due to an inability to
     * instantiate the context class
     */
    public ConstructedGwtTest<T> test(String testName) {
        if (testPhase == null) {
            try {
                context = contextClass.getDeclaredConstructor().newInstance();
                context.testName = testName;
                testPhase = TestPhase.CONSTRUCTED;
                return new ConstructedGwtTest<>(context);
            }
            catch (Exception e) {
                throw new TestConstructionException("Can't construct test", e);
            }
        }
        throw new MalformedTestException("Can't call test() more than once.");
    }

    private String getCallingMethodName() {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Optional<String> callingMethodName = stackWalker.walk(frames -> frames
                .skip(2) // first frame is this method, second frame is the method calling this method
                .findFirst()
                .map(StackWalker.StackFrame::getMethodName));
        return callingMethodName.orElse("unknown");
    }

}
