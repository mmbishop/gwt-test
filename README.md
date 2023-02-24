# gwt-test
Given-When-Then testing framework for Java

## Overview
gwt-test provides an easy-to-use framework for writing unit and integration tests in the Given-When-Then format.

## Background
The Given-When-Then testing format is based on the [Gherkin](https://cucumber.io/docs/gherkin/) language for specifying test scenarios and business rules. An
example of a test scenario written in Gherkin is:

**Scenario**: Customer purchases Product and receives Invoice   
**Given** A Customer  
**And** A Product  
**When** the Customer purchases the Product  
**Then** an Invoice is created  
**And** the Invoice is emailed to the Customer

## Writing tests using gwt-test

The above scenario can be expressed using gwt-test as follows:

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

## Elements of gwt-test

Let's take a look at the elements that comprise gwt-test.

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
which is a test name. If you pass in a test name, it will be assigned to the testName property in the context. If you don't, the testName is the name of the
method in which ```test``` is called.

```test``` returns the same GwtTest instance that it was called on. From here, you can chain the _given_, _when_ and _then_ clauses that make up your test.

### Specifying a test

GwtTest has methods called ```given```, ```when```, ```then``` and ```and``` that you call in order to specify a test. The arguments to these methods are
functions and in some cases, arguments to those functions. The following types of functions are available (where ```T``` represents the test context class 
you used to instantiate the GwtTest instance):

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

```GwtFunctionWithArgument<T, V>``` takes an instance of the context class and an object of type ```V``` as its arguments. An example of a
```GwtFunctionWithArgument``` declaration is:

```
private final GwtFunctionWithArgument<TestContext, Integer> the_calculated_product_is = (context, expectedProduct) -> {
    assertThat(context.product, is(expectedProduct));
};
```

Finally, ```GwtFunctionWithArguments<T, V>``` takes an instance of the context class and a varargs array of type V as its arguments. An example of a
```GwtFunctionWithArguments``` declaration is:

```
private final GwtFunctionWithArguments<TestContext, String> the_numbers = (context, numbers) -> context.numbers = numbers;
```

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

### Why snake case?

You've noticed that the test method and function names are specified using snake case. You don't have to use snake case; camel case is perfectly fine. I use
snake case in my test classes because it's possible (though probably unlikely) that I may need to ask a domain expert or business analyst to look at a test
to make sure I'm covering all of the cases. They are much more likely to want to read snake case than camel case. All I would ask them to read is the test
methods (those annotated with @Test). Any other supporting methods I write will be named using camel case since only developers will be looking at that code.

## Examples

You can see examples of unit tests that are written with gwt-test in the [unit test package](src/test/java/com/mikebishop/gwttest/core).