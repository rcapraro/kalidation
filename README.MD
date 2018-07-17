**Objective**

Creation of a validation DSL which allows this kind of fluent code:

```kotlin
val com.capraro.kalidation.spec = validationSpec {
     com.capraro.kalidation.constraints<Foo> {
         field(Foo::bar) {
             notBlank()
             size(min = 5)
         }
         field(Foo::baz) {
             min(3)
         }
     }
 }
 ```