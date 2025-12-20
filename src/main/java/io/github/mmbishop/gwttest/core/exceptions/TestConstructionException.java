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

package io.github.mmbishop.gwttest.core.exceptions;

/**
 * An exception that is thrown when a GWT test can't be constructed. This typically happens when a test's
 * context class is not public and thus is not accessible to instantiate.
 */
public class TestConstructionException extends RuntimeException {

    /**
     * Constructs a new TestConstructionException with the specified detail message and cause.
     * <p>
     * This constructor is typically used when a GWT test cannot be constructed due to an
     * underlying exception, such as when a test's context class is not accessible for instantiation.
     *
     * @param message the detail message explaining why the test construction failed
     * @param cause   the underlying cause of the construction failure
     */
    public TestConstructionException(String message, Throwable cause) {
        super(message, cause);
    }

}
