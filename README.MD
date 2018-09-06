![Kalidation](Kalidation-logo.png?raw=true)

*Kalidation = A Kotlin validation DSL*

[![Build Status](https://travis-ci.org/rcapraro/kalidation.svg?branch=master)](https://travis-ci.org/rcapraro/kalidation)

**Objective**

Creation of a validation DSL which allows this kind of fluent code:

```kotlin
val spec = validationSpec {
       constraints<Foo> {
         field(Foo::bar) {
             notBlank()
             inValues("GREEN", "WHITE", "RED")
         }
         field(Foo::baz) {
             min(3)
             email()
         }
     }
 }
```
 
This DSL decouples your domain classes from any validation framework and annotations and, as such, respect the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html).
  
**Inspiration**

https://blog.sourced-bvba.be/article/2018/01/13/kotlin-validation/

 **Usage**
 
 ```kotlin
val spec = validationSpec {
            constraints<MyClass> {
                property(MyClass::field1) {
                    notBlank()
                    inValues("GREEN", "WHITE", "RED")
                    size(3, 5)
                }
                property(MyClass::field2) {
                    regexp("[A-Za-z0-9]+")
                }
                property(MyClass::field3) {
                    future()
                }
                property(MyClass::innerClass) {
                    valid()
                }                     
            }
            constraints<InnerClass> {
                property(InnerClass::amount) {
                    negativeOrZero()
                }                  
            }            
        }
        
val myClass = MyClass("BLUE", "foobar", LocalDateTime.parse("2017-12-03T10:15:30"))

val validated = spec.validate(myClass)     
```

In this example, `validated` is an [Arrow](https://arrow-kt.io) `Validated` object, which we can transform through Arrow built-in functions: `when`, `fold`, `getOrElse`, `map`, etc.

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
 
_Structure of the validation result_:

The validation result structure is a `Set` of `ValidationResult` instances.
 ```kotlin
 data class ValidationResult(val fieldName: String, val message: String)
```
The `ValidationResult` object contains the name of the field in error, and the i18n corresponding message.
 
**Implemented validation functions**
 
***All classes***

 * notNull()
 * isNull()
 * valid(), used for cascading validation (on an inner class)
 
***Array***
 
  * size(val min: Int, val max: Int)
  * notEmpty()
 
***Iterable (Collection, List, Set, etc.)***
  
  * size(val min: Int, val max: Int)
  * notEmpty()
  
***Boolean***
  
  * assertTrue()
  * assertFalse()
 
***CharSequence (String, StringBuilder, StringBuffer, etc.)***
  
  * notBlank()
  * notEmpty() 
  * size(val min: Int, val max: Int) 
  * regexp(val regexp: String)
  * email()
  * phoneNumber(val regionCode: String)
  * inValues(val values: List<String>)
  * min(val value: Long)
  * max(val value: Long)
  * decimalMin(val value: String, val inclusive: Boolean)
  * decimalMax(val value: String, val inclusive: Boolean)
  * digits (val integer: Int, val fraction: Int)    
     
 
***Number (Integer, Float, Long, BigDecimal, BigInteger, etc.)***
  
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