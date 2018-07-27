*Kalidation = A Kotlin validation DSL*

![Kalidation](Kalidation-logo.png?raw=true)

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
 
This DSL allows decoupling from any validation framework and annotations in your domain classes, and as such, respect the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html).
  
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
            }
        }
        
val dslTest = MyClass("BLUE", "foobar", LocalDateTime.parse("2017-12-03T10:15:30"))

val validated = spec.validate(dslTest)     
```

In this example, `validated` is a Arrow `Validated` object, which we can transform through Arrow built-in functions: `when`, `fold`, `getOrElse`, `map`, etc.

See [Arrow Validated](https://arrow-kt.io/docs/datatypes/validated/#validated) for more documentation.
 
**Implemented validation functions**
 
***All classes***

 * notNull()
 * isNull()
 
***Array***
 
  * size(val min: Int, val max: Int)
  * notEmpty()
 
***Iterable (Collection, List, Set, etc.)***
  
  * size(val min: Int, val max: Int)
  * notEmpty()
  
***Boolean***
  
  * assertTrue()
  * assertFalsey()
 
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
  
**Temporal (LocalDate, LocalDateTime, ZonedDateTime, etc.)**
      
  * future()
  * past() 
  * futureOrPresent()
  * pastOrPresent()