# gwt-test
Given-When-Then testing framework for Java

## Overview
gwt-test provides an easy-to-use framework for writing unit and integration tests in the Given-When-Then format.

## How to Use
To use gwt-test, include it as a dependency as follows:

**Maven**
```
<dependency>
    <groupId>io.github.mmbishop</groupId>
    <artifactId>gwt-test</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

**Gradle**
```
testImplementation 'io.github.mmbishop:gwt-test:1.0.0'
```

gwt-test requires Java 17 or later.

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

## Writing tests using gwt-test

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
writing tests using something like jest-gwt in Typescript.

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
to the constructor (```TestContext``` in this example) is instantiated. ```test``` has two signatures. One takes no arguments and the other takes one argument
which is a test name. If you pass in a test name, it will be assigned to the ```testName``` property in the context. If you don't, ```testName``` is the name of the
method in which ```test``` is called.

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
private final GwtFunctionWithArguments<TestContext, Integer> the_numbers = (context, numbers) -> context.numbers = numbers;
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

#### Exception handling

The base [Context](src/main/java/io/github/mmbishop/gwttest/model/Context.java) class has a property called ```thrownException``` that stores any exception that is
thrown during the execution of a _when_ function. To check if an exception was thrown, you can simply check that property. For example,

```
private final GwtFunction<TestContext> an_exception_is_thrown = context -> assertNotNull(context.thrownException);
```

## An example test class using gwt-test

The following is a trivial but valid example of a test class that uses gwt-test.

```java
package gwttest.example;

import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DivisionTest {

    private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);

    @Test
    void quotient_of_two_numbers_is_calculated() {
        gwt.test()
                .given(a_dividend, 12.0)
                .and(a_divisor, 4.0)
                .when(dividing_the_dividend_by_the_divisor)
                .then(the_quotient_is, 3.0);
    }

    @Test
    void dividing_a_number_by_zero_results_in_infinity() {
        gwt.test()
                .given(a_dividend, 6.0)
                .when(dividing_the_dividend_by_zero)
                .then(the_quotient_is_infinity);
    }

    private final GwtFunctionWithArgument<TestContext, Double> a_dividend  
            = (context, dividend) -> context.dividend = dividend;

    private final GwtFunctionWithArgument<TestContext, Double> a_divisor  
            = (context, divisor) -> context.divisor = divisor;

    private final GwtFunction<TestContext> dividing_the_dividend_by_the_divisor  
            = context -> context.quotient = context.dividend / context.divisor;
    
    private final GwtFunction<TestContext> dividing_the_dividend_by_zero  
            = context -> context.quotient = context.dividend / 0.0;

    private final GwtFunctionWithArgument<TestContext, Double> the_quotient_is  
            = (context, expectedQuotient) -> assertThat(context.quotient, is(expectedQuotient));
    
    private final GwtFunction<TestContext> the_quotient_is_infinity  
            = context -> assertThat(context.quotient, is(Double.POSITIVE_INFINITY));

    public static final class TestContext extends Context {
        Double dividend;
        Double divisor;
        Double quotient;
    }
}
```

Note: The JUnit and Hamcrest dependencies supporting the imports in the example are test-scoped in the gwt-test library, so you won't get them as 
transitive dependencies. They are not required (though JUnit will almost certainly be needed), but if you want those dependencies you will need to declare them 
in your project. Hamcrest is recommended as its matcher methods are very useful for writing assertions.

### Why snake case?

You've noticed that the test method and function names are specified using snake case. You don't have to use snake case; camel case is perfectly fine. I use
snake case in my test classes because it's possible that I may need to ask a domain expert or business analyst to look at a test
to make sure I'm covering all of the cases. They are much more likely to want to read snake case than camel case. All I would ask them to read is the test
methods (those annotated with @Test). Any other supporting methods I write will be named using camel case since only developers will be looking at that code.

## Examples

You can see examples of unit tests that are written with gwt-test in the [unit test package](src/test/java/io/github/mmbishop/gwttest).