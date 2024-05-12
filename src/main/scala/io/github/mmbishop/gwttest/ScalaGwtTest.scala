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

package io.github.mmbishop.gwttest

import io.github.mmbishop.gwttest.functions.{GwtFunction, GwtFunctionWithArgument, GwtFunctionWithArguments}
import io.github.mmbishop.gwttest.model.Context

/**
 * The core class for building Given-When-Then tests.
 * @tparam T a subclass of [[Context]] that contains the fields used in test code.
 */
class ScalaGwtTest[T <: Context] {

  private var testObject: GwtTest[T] = _

  /**
   * Instantiates a <pre>ScalaGwtTest</pre> object.
   * @param contextClass the class of the context object used by the test
   */
  def this(contextClass: Class[T]) = {
    this()
    testObject = GwtTest[T](contextClass)
  }

  /**
   * Initializes the <pre>ScalaGwtTest</pre> object.
   *
   * @return this <pre>ScalaGwtTest</pre> object
   * @throws TestConstructionException could not construct the test, most likely due to an inability to
   *                                   instantiate the context class
   */
  def test(): ScalaGwtTest[T] = {
    testObject = testObject.test()
    this
  }

  /**
   * Initializes the <pre>ScalaGwtTest</pre> object.
   * @param testName the name of the test
   * @return this <pre>ScalaGwtTest</pre> object
   * @throws TestConstructionException could not construct the test, most likely due to an inability to
   *                                   instantiate the context class
   */
  def test(testName: String): ScalaGwtTest[T] = {
    testObject = testObject.test(testName)
    this
  }

  /**
   * Invokes the given functions with the context object.
   *
   * @param gwtFunctions <pre>GwtFunction</pre>s that contain logic to be performed as part of the Given clause
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Given(gwtFunctions: GwtFunction[T]*): ScalaGwtTest[T] = {
    testObject = testObject.callGiven(gwtFunctions: _*)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the Given clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param arg         an argument of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Given[V](gwtFunction: GwtFunctionWithArgument[T, V], arg: V): ScalaGwtTest[T] = {
    testObject = testObject.callGiven(gwtFunction, arg)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the Given clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param args        arguments of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Given[V](gwtFunction: GwtFunctionWithArguments[T, V], args: V*): ScalaGwtTest[T] = {
    testObject = testObject.callGiven(gwtFunction, args: _*)
    this
  }

  /**
   * Invokes the given functions with the context object.
   *
   * @param gwtFunctions <pre>GwtFunction</pre>s that contain logic to be performed as part of the When clause
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def When(gwtFunctions: GwtFunction[T]*): ScalaGwtTest[T] = {
    testObject = testObject.callWhen(gwtFunctions: _*)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the When clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param arg         an argument of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def When[V](gwtFunction: GwtFunctionWithArgument[T, V], arg: V): ScalaGwtTest[T] = {
    testObject = testObject.callWhen(gwtFunction, arg)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the When clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param args        arguments of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def When[V](gwtFunction: GwtFunctionWithArguments[T, V], args: V*): ScalaGwtTest[T] = {
    testObject = testObject.callWhen(gwtFunction, args: _*)
    this
  }

  /**
   * Invokes the given functions with the context object.
   *
   * @param gwtFunctions <pre>GwtFunction</pre>s that contain logic to be performed as part of the Then clause
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Then(gwtFunctions: GwtFunction[T]*): ScalaGwtTest[T] = {
    testObject = testObject.callThen(gwtFunctions: _*)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the Then clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param arg         an argument of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Then[V](gwtFunction: GwtFunctionWithArgument[T, V], arg: V): ScalaGwtTest[T] = {
    testObject = testObject.callThen(gwtFunction, arg)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of the Then clause. This function takes
   *                    an argument of type <pre>V</pre> and an instance of a subclass of [[Context]].
   * @param args        arguments of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def Then[V](gwtFunction: GwtFunctionWithArguments[T, V], args: V*): ScalaGwtTest[T] = {
    testObject = testObject.callThen(gwtFunction, args: _*)
    this
  }

  /**
   * Invokes the given function with the context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of an And clause attached
   *                    to a Given, When or Then
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def And(gwtFunction: GwtFunction[T]): ScalaGwtTest[T] = {
    testObject = testObject.and(gwtFunction)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of an And clause attached
   *                    to a Given, When or Then.  This function takes an argument of type <pre>V</pre>
   *                    and an instance of a subclass of [[Context]].
   * @param arg         an argument of type <pre>V</pre>
   * @return this <pre>ScalaGwtTest</pre> object
   */
  def And[V](gwtFunction: GwtFunctionWithArgument[T, V], arg: V): ScalaGwtTest[T] = {
    testObject = testObject.and(gwtFunction, arg)
    this
  }

  /**
   * Invokes the given function on the given argument and context object.
   *
   * @param gwtFunction <pre>GwtFunction</pre> that contains logic to be performed as part of an And clause attached
   *                    to a Given, When or Then.  This function takes an argument of type <pre>V</pre>
   *                    and an instance of a subclass of [[Context]].
   * @param args        arguments of type <pre>V</pre>
   * @return this <pre>GwtTest</pre> object
   */
  def And[V](gwtFunction: GwtFunctionWithArguments[T, V], args: V*): ScalaGwtTest[T] = {
    testObject = testObject.and(gwtFunction, args: _*)
    this
  }

}
