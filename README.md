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
```

The identifiers are functions that are instances of ```GwtFunction``` if the function takes no arguments, or ```GwtFunctionWithArgument``` if the
function takes one argument. For example, the _given_ function ```a_customer``` might look something like this:

```
private final GwtFunction<TestContext> a_customer = context -> context.customer = createCustomer();
```

The ```context``` argument to the function comes from the declaration of the ```gwt``` object, which looks like this:

```
private final GwtTest<TestContext> gwt = new GwtTest<>(TestContext.class);
```

where ```TestContext``` is a class extending ```Context``` that you define in your test class. This class contains everything you need to run your unit tests.
The name of this context class can be anything you want. We're using ```TestContext``` as an example. To support the above unit test, you might define that class as follows:

```
private static class TestContext extends Context {
    Customer customer;
    Invoice invoice;
    Product product;
}
```

When ```GwtTest```'s ```test``` method is called, the class you pass to the constructor (```TestContext``` in this example) is instantiated and the instance is passed to every 
_given_, _when_ and _then_ function in your test method.

You can see examples of unit tests that are written with gwt-test in the [unit test package](src/test/java/com/mikebishop/gwttest/core).