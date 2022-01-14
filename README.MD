![Kalidation](Kalidation-logo.png?raw=true)

*Kalidation = A Kotlin validation DSL*

[![Maven artifact](https://img.shields.io/maven-central/v/io.github.rcapraro/kalidation)](https://search.maven.org/artifact/io.github.rcapraro/kalidation)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/rcapraro/kalidation)](https://github.com/rcapraro/kalidation/releases/latest)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/rcapraro/kalidation/Publish%20package%20to%20GitHub%20Packages)
![GitHub](https://img.shields.io/github/license/rcapraro/kalidation)

**Objective**

Creation of a validation DSL which allows this kind of fluent code:

```kotlin
val spec = validationSpec {
    constraints<Foo> {
        property(Foo::bar) {
            notBlank()
            inValues("GREEN", "WHITE", "RED")
        }
        property(Foo::bax) {
            min(3)
            email()
        }
        property(Foo::baz) {
            validByScript(lang = "groovy", script = "baz.validate()", alias = "baz")
        }
        returnOf(Foo::validate) {
            assertTrue()
        }
        returnOf(Foo::total) {
            min(10)
        }
    }
}
```

This DSL does _Type Checking_ on the properties of the bean to validate, ie constraints on `Foo` should only contain
properties of `Foo`.

It also does _Type Checking_ on the rule: eg: an `email()` constraint is not applicable to an numeric property, so you
shouldn’t be allowed to put a constraint to such a property.

Furthermore, this DSL decouples your domain classes from any validation framework and annotations and, as such, respect
the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html).

**Usage**

For **Kalidation** to work, you need to add the `arrow-core`, `jakarta-validation` and `jakarta-el` dependencies.

Example for gradle:
```
implementation("io.github.rcapraro:kalidation:1.8.0")
implementation("io.arrow-kt:arrow-core:0.13.1")
implementation("jakarta.validation:jakarta.validation-api:3.0.0")
implementation("org.glassfish:jakarta.el:4.0.1")
```

You can then build a `validationSpec` object with the DSL:

```kotlin
val spec = validationSpec(messageBundle = "MyMessages", locale = Locale.FRENCH) {
    constraints<MyClass> {
        property(MyClass::color) {
            notBlank()
            inValues("GREEN", "WHITE", "RED")
            size(3, 5)
        }
        property(MyClass::token) {
            regexp("[A-Za-z0-9]+")
        }
        property(MyClass::date) {
            future()
        }
        returnOf(Foo::validate) {
            assertTrue()
        }
        property(MyClass::innerClass) {
            valid()
        }
    }
    constraints<InnerClass> {
        property(InnerClass::amount) {
            negativeOrZero()
        }
        property(InnerClass::emailList) {
            notEmpty()
            eachElement {
                notNull()
                email()
            }
        }
    }
}

val myClass = MyClass("BLUE", "foobar", LocalDateTime.parse("2017-12-03T10:15:30"), ...)

val validated = spec.validate(myClass) 
```

In this example, `validated` is an [Arrow](https://arrow-kt.io) `Validated` object, which we can transform through Arrow
built-in functions: `when`, `fold`, `getOrElse`, `map`, etc.

There is also an alternative validation-function called `validateNel`, which returns the Arrow
type `NonEmptyList<ValidationResult>`.

See [Arrow Validated](https://arrow-kt.io/docs/datatypes/validated/#validated) for more documentation.

_Example with `fold`_:

```kotlin
val validated = spec.validate(myClass)
validated.fold(
    { throw ValidationException(it) },
    { return it }
)
```

_Example with `when`_:

```kotlin
val validated = spec.validate(myClass)
when (validated) {
    is Valid -> return validated.a
    is Invalid -> throw ValidationException(validated.e)
}
```

_Monad comprehension_:

You can also use the right side of the validated result to operate a monad comprehension on the validated class:

```kotlin
val validated = spec.validate(myClass)
    .map { it.doSomething() } //it is myClass
//etc
```

_Structure of the validation result_:

The validation result structure is a `Set` of `ValidationResult` instances.

 ```kotlin
data class ValidationResult(
    val fieldName: String,
    val invalidValue: Any?,
    val messageTemplate: String,
    val message: String
)
```

The `ValidationResult` object contains the name and the value of the field in error, the message template and the i18n
corresponding message.

**Complete example**

The following examples illustrates the use of **Kalidation** in a typical **Spring Boot** application, structured in a *Clean Architecture* way:

https://github.com/rcapraro/cleanarch

**Implemented validation functions on properties**

***All classes***

* notNull()
* isNull()
* valid(), used for cascading validation (on an inner class)
* validByScript(lang: String, script: String, alias: String = "_this", reportOn: String = "") - *supports javascript,
  jexl and groovy scripts which returns a **Boolean***

***Array***

* size(val min: Int, val max: Int)
* notEmpty()

***Collections (List, Set, etc.)***

* size(val min: Int, val max: Int)
* notEmpty()
* subSetOf(val completeValues: List<String>)

***Maps***

* size(val min: Int, val max: Int)
* notEmpty()
* hasKeys(val keys: List<String>)

***Boolean***

* assertTrue()
* assertFalse()

***CharSequence (String, StringBuilder, StringBuffer, etc.)***

* notBlank()
* notEmpty()
* size(val min: Int, val max: Int)
* size(size: Int)
* regexp(val regexp: String)
* email()
* phoneNumber(val regionCode: String)
* inValues(val values: List<String>)
* negativeOrZero()
* positiveOrZero()
* negative()
* positive()
* range(val min: Long, val max: Long)
* min(val value: Long)
* max(val value: Long)
* decimalMin(val value: String, val inclusive: Boolean)
* decimalMax(val value: String, val inclusive: Boolean)
* digits (val integer: Int, val fraction: Int)
* iso8601Date()
* inIso8601DateRange(startDate: String, stopDate: String)

***Number (Integer, Float, Long, BigDecimal, BigInteger, etc.)***

* range(val min: Long, val max: Long)
* negativeOrZero()
* positiveOrZero()
* negative()
* positive()
* min(val value: Long)
* max(val value: Long)
* decimalMin(val value: String, val inclusive: Boolean)
* decimalMax(val value: String, val inclusive: Boolean)
* digits (val integer: Int, val fraction: Int)

***Temporal (LocalDate, LocalDateTime, ZonedDateTime, etc.)***

* future()
* past()
* futureOrPresent()
* pastOrPresent()

> For all methods, an optional *message: String?* parameter can be used to override the resource bundle message.


**Validation on method return type**

It is also possible to specify a validation on a return type of a method:

```kotlin
returnOf(Foo::validate) {
    notNull()
    assertTrue()
    //etc...
}
```

The method `returnOf` accepts an optional `alias` parameter to report the violation on a specific property rather than
the method.

In this example, if the method validate returns `false`, the `ValidationResult` object will look like:

```
Invalid(e=[ValidationResult(
    fieldName=validate.<return value>, 
    invalidValue=false, 
    messageTemplate={jakarta.validation.constraints.AssertTrue.message}, 
    message=doit être vrai)]
)
```

**Validation of containers (List, Maps, Sets, etc)**

It is possible to validate each property inside a container:

```kotlin
eachElement(Foo::emails) {
    notNull()
    email()
    //etc...
}
```

In case of more complex containers (ex: Map of List), a `NonEmptyList` of *indexes* enables a navigation inside the
container types to validate.

For example, to validate the `List<String?>` of a `Map<String, List<String?>`, we must write the following validation:

````kotlin
eachElement(String::class, NonEmptyList(1, 0)) {
    notNull()
    email()
    //etc...
}
````

       