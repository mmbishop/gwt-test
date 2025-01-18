# gwt-test
Given-When-Then testing framework for Java, with support for Groovy, Scala and Kotlin

## Table of Contents

[Overview](#overview)  
[How to Use](#how-to-use)  
[Language Support](#language-support)  
[Background](#background)  
[Writing Tests Using gwt-test](#writing-tests-using-gwt-test)  
[Elements of gwt-test](#elements-of-gwt-test)  
[Exception handling](#exception-handling)  
[Example Test Classes Using gwt-test](#example-test-classes-using-gwt-test)  
[Logging](#logging)  
[See Also](#see-also)

## Overview
gwt-test provides an easy-to-use framework for writing unit and integration tests in the Given-When-Then format.

## How to Use
To use gwt-test, include it as a dependency as follows:

**Maven**
```
<dependency>
    <groupId>io.github.mmbishop</groupId>
    <artifactId>gwt-test</artifactId>
    <version>1.3.1</version>
    <scope>test</scope>
</dependency>
```

**Gradle**
```
testImplementation 'io.github.mmbishop:gwt-test:1.3.1'
```

**sbt**
```
"io.github.mmbishop" % "gwt-test" % "1.3.1" % Test
```

## Language Support
| Language  | Version                       |
|-----------|-------------------------------|
| Java      | [17,)                         |
| Groovy    | [3,)                          |
| Kotlin    | [1.6,), [2,)                  |
| Scala     | [3.0,), [2.13.6,), [2.12.15,) |

## Background
The Given-When-Then testing format is based on the [Gherkin](https://cucumber.io/docs/gherkin/) language for specifying test scenarios and business rules. An
example of a test scenario written in Gherkin is:

**Scenario**: Customer purchases Product and receives Invoice   
**Given** A Customer  
**And** A Product  
**When** the Customer purchases the Product  
**Then** an Invoice is created  
**And** the Invoice is emailed to the Customer

Writing unit and integration tests in the Given-When-Then format provides several advantages over the traditional Arrange-Act-Assert approach, including:

* Tests focus on the behavior of the system under test rather than on the mechanics of the underlying code.
* A reduced coupling of tests to implementation, so that changes to the implementation don't often require a change to tests. An implementation change to improve performance
or fix a bug isn't changing the required behavior of the code, so the tests shouldn't change.
* The test code is more modular, leading to code reuse.
* The tests read like acceptance criteria, which can facilitate more conversations between different stakeholders such as developers, QA, product owners and business users.

## Writing Tests Using gwt-test

The product purchase scenario shown above can be expressed using gwt-test as follows:

```
@Test
void customer_purchases_product_and_receives_invoice() {
    gwt.test()
        .given(a_customer)
        .and(a_product)
        .when(the_customer_purchases_the_product)
        .then(an_invoice_is_created)
        .and(the_invoice_is_emailed_to_the_customer);
}
```

The test can also be written like this:

```
@Test
void customer_purchases_product_and_receives_invoice() {
    gwt.test()
        .given(
            a_customer,
            a_product
        )
        .when(the_customer_purchases_the_product)
        .then(
            an_invoice_is_created,
            the_invoice_is_emailed_to_the_customer
        );
}
```

The first form is closer to canonical Gherkin and may be easier to read for most people, while the second form may be more familiar to developers used to 
writing tests using a package like [jest-gwt](https://github.com/devzeebo/jest-gwt).

## Elements of gwt-test

Let's take a look at the elements that comprise a test written with gwt-test.

### The context

Each test written with gwt-test has a context object that is an instance of a class that extends [Context](src/main/java/io/github/mmbishop/gwttest/model/Context.java).
The base class Context contains some test metadata that may be useful, including the name of the test being executed, and the current test phase (_given_, _when_
or _then_). The context class you define in your test will contain everything needed to run your test methods. The name of your context class is arbitrary. The
only requirements are that it extend Context, and it must be public so that gwt-test can instantiate it. An example of a test context class is the following:

```
public static class TestContext extends Context {
    Customer customer;
    Invoice invoice;
    Product product;
}
```

### The gwt-test instance

You must obtain an instance of [GwtTest](src/main/java/io/github/mmbishop/gwttest/core/GwtTest.java) that you will use to run the tests. Typically, this is a 
class-wide field in your test class. To get a GwtTest instance that uses the above context class, use the following declaration:

```
private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);
```

This line of code instantiates a gwt-test instance that uses the TestContext class to create context objects.

### Initializing a test

A test is started by calling the ```test``` method on the GwtTest instance, like this: ```gwt.test()```. When ```test``` is called, the class you pass 
to the constructor (```TestContext``` in this example) is instantiated. Thus, each test is given a fresh new instance of the context class, which 
enables each test to be independent of the other tests in the suite.

```test``` has two signatures. One takes no arguments and the other takes one argument which is a test name. If you pass in a test name, it will be 
assigned to the ```testName``` property in the context. If you don't, ```testName``` is the name of the method in which ```test``` is called.

```test``` returns the same GwtTest instance that it was called on. From here, you can chain the _given_, _when_ and _then_ clauses that make up your test.

### Specifying a test

GwtTest has methods called ```given```, ```when```, ```then``` and ```and``` that you call in order to specify a test. The arguments to these methods are
functions and in some cases, arguments to those functions. The following types of functions are available (where ```T``` represents the test context class 
you used to instantiate the GwtTest instance):

#### GwtFunction

```GwtFunction<T>``` takes an instance of the context class as its sole argument. This context instance is passed in by gwt-test, so you don't have to worry about it.
An example of a ```GwtFunction``` declaration is:

```
private final GwtFunction<TestContext> multiplying_the_numbers_together = context -> {
    context.product = 1;
    for (Integer number : context.numbers) {
        context.product *= number;
    }
};
```

#### GwtFunctionWithArgument

```GwtFunctionWithArgument<T, V>``` takes an instance of the context class and an object of type ```V``` as its arguments. An example of a
```GwtFunctionWithArgument``` declaration is:

```
private final GwtFunctionWithArgument<TestContext, Integer> the_calculated_product_is = (context, expectedProduct) -> {
    assertThat(context.product, is(expectedProduct));
};
```

#### GwtFunctionWithArguments

Finally, ```GwtFunctionWithArguments<T, V>``` takes an instance of the context class and a varargs array of type V as its arguments. An example of a
```GwtFunctionWithArguments``` declaration is:

```
private final GwtFunctionWithArguments<TestContext, Integer> the_numbers = (context, numbers) -> {
    context.numbers = new ArrayList<Integer>();
    for (Integer number : numbers) {
        context.numbers.add(number);
    }
}
```

#### Using the functions in a test

A test using the above functions may look something like this:

```
@Test
void product_of_multiple_numbers_is_calculated() {
    gwt.test()
            .given(the_numbers, 2, 3, 4)
            .when(multiplying_the_numbers_together)
            .then(the_calculated_product_is, 24);
}
```

### Multiple related cases in a test

You can have multiple when-then clauses in a single test:

```
@Test
void numbers_can_be_multiplied_and_divided() {
    gwt.test()
            .given(the_numbers, 12, 4)
            .when(multiplying_the_numbers)
            .then(the_product_is, 48)
            .when(dividing_the_numbers)
            .then(the_quotient_is, 3);
}
```

## Exception handling

Any exception thrown during a test will be caught and rethrown by gwt-test (in which case the test fails) unless the exception class is declared as an 
expected exception. To declare an exception as expected, use the ```expectingException``` method as follows:

```
gwt.test().expectingException(ExpectedExceptionClass.class)
```

If an exception is thrown during the test, gwt-test will check if the thrown exception is an instance of the expected exception class 
(ExpectedExceptionClass in this example) or one of its subclasses. If it is, then the test continues. If the thrown exception is of a different class 
from the one that is expected, or no expected exception class has been declared, gwt-test will soften the exception by wrapping it in an instance of
```UnexpectedExceptionCaughtException``` and throw it, which will result in the test failing.

If an exception is declared via ```expectingException``` but no exception is thrown during the test, the test will fail.

The base [Context](src/main/java/io/github/mmbishop/gwttest/model/Context.java) class has a property called ```thrownException``` that stores any exception that is
thrown during the execution of a test. To check if an expected exception was thrown, you can simply check that property. For example,

```
private final GwtFunction<TestContext> an_exception_is_thrown = context -> assertNotNull(context.thrownException);
```

The base [Context](src/main/java/io/github/mmbishop/gwttest/model/Context.java) class also has a propery called ```expectedExceptionClass``` that
stores the exception class declared in the ```expectingException``` method.

Exceptions are logged by gwt-test using the [SLF4J](https://www.slf4j.org/) API, but no implementation is provided in order to reduce the 
risk of conflicts with logging implementations that you're using. To see exception log messages in your tests, you will need to have an SLF4J 
implementation among your dependencies.

## Example Test Classes Using gwt-test

The following are trivial but valid examples of a test class that uses gwt-test.

- [Java](doc/java-example.md)
- [Groovy](doc/groovy-example.md)
- [Scala](doc/scala-example.md)
- [Kotlin](doc/kotlin-example.md)

Note: The JUnit and Hamcrest dependencies supporting the imports in the example are test-scoped in the gwt-test library, so you won't get them as 
transitive dependencies. They are not required (though JUnit will almost certainly be needed), but if you want those dependencies you will need to declare them 
in your project. Hamcrest is recommended as its matcher methods are very useful for writing assertions.

### Why Snake Case?

You've probably noticed that the test method and function names are specified using snake case. You don't have to use snake case; camel case is perfectly fine. I use
snake case in my test classes because it's possible that I may need to ask a domain expert or business analyst to look at a test
to make sure I'm covering all of the cases. They are much more likely to want to read snake case than camel case. All I would ask them to read is the test
methods (those annotated with @Test). Any other supporting methods I write will be named using camel case since I expect only developers to look at that code.

## Examples

You can see examples of unit tests that are written with gwt-test in the [unit test package](src/test/java/io/github/mmbishop/gwttest).

## Logging

gwt-test supports logging in your tests via the [SLF4J](https://www.slf4j.org/) framework. However, gwt-test doesn't provide the slf4j library because
doing so would cause a resolution error in projects with module descriptors that require org.slf4j. Thus, if you want logging in your tests, you'll need 
to include org.slf4j:slf4j-api and an SLF4J implementation (such as log4j-slf4j2-impl) in your dependencies.

## See Also

- [jest-gwt](https://github.com/devzeebo/jest-gwt) is a package that provides excellent support for Given-When-Then tests in your Typescript projects. 