*Kalidation = A Kotlin validation DSL*

![Kalidation](Kalidation-logo.png?raw=true)

**Objective**

Creation of a validation DSL which allows this kind of fluent code:

```kotlin
val spec = validationSpec {
     com.capraro.kalidation.constraints<Foo> {
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
        
val dslTest = StringAndBooleanTestClass("BLUE", "foobar")

val validated = spec.validate(dslTest)     

   
  ```

In this example, validated is a Arrow `Validated` object, which we can transform through Arrow built-in functions: `when`, `fold`, `getOrElse`, `map`, etc.

See [Arrow Validated](https://arrow-kt.io/docs/datatypes/validated/#validated) for more documentation.
 
 **Implemented rules**
 
 // Work in progress
 