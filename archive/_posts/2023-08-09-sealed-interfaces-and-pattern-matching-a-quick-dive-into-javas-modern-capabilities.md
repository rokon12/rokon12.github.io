---
title: 'Sealed Interfaces and Pattern Matching: A Quick Dive into Java’s Modern Capabilities'
original_url: 'https://bazlur.ca/2023/08/09/sealed-interfaces-and-pattern-matching-a-quick-dive-into-javas-modern-capabilities/'
date_published: '2023-08-09T00:00:00+00:00'
date_scraped: '2025-02-15T11:26:14.16843723'
tags: ["tutorial", "java", "ai", "tools", "programming"]
featured_image: images/ae9ff0aa-e74c-4de4-92b3-e358ab55de0a.jpeg
---

![](images/ae9ff0aa-e74c-4de4-92b3-e358ab55de0a.jpeg)

Sealed Interfaces and Pattern Matching: A Quick Dive into Java's Modern Capabilities
====================================================================================

**Sealed classes in Java are a new feature that provides a way to restrict the classes that can inherit from a superclass or extend an interface. This new language feature enhances the encapsulation and provides more control to developers over their codebase.**

In this tutorial, we will explore sealed classes, how to use them to find all subclasses, and how to apply pattern matching in a practical context.

Let's start with a basic interface definition named `Shape`.

```
public interface Shape {
    double getArea();
}
```

In a conventional scenario, any class can implement `Shape`, and finding all classes that do this is not straightforward. However, with the introduction of sealed classes, we can specify precisely which classes are allowed to implement an interface.

Defining a Sealed Interface
---------------------------

Here is how you can declare a sealed interface with permitted subclasses:

```
public sealed interface Shape permits Circle, Rectangle, Square {
    double getArea();
}
```

In the example above, we declared `Shape` as a sealed interface and explicitly specified that only `Circle`, `Rectangle`, and `Square` classes can implement `Shape`. This is a powerful feature, as it gives us more control over our class hierarchy and prevents unwanted class implementations.

Finding All Permitted Subclasses
--------------------------------

With a sealed interface, finding all subclasses or implementors becomes straightforward:

```
var permittedSubclasses = Shape.class.getPermittedSubclasses();
for (Class<?> subclass : permittedSubclasses) {
    System.out.println("subclass = " + subclass);
}
```

The method `getPermittedSubclasses()` returns an array of `Class` objects representing the permitted subclasses of `Shape`. We can then loop through the array and print out all the subclasses.

Pattern Matching with Sealed Classes
------------------------------------

Pattern matching is another powerful feature in Java that goes hand-in-hand with sealed classes. With pattern matching, we can perform operations based on the type of the object:  

```
switch (shape){
    case Circle circle -> System.out.println("circle = " + circle);
    case Rectangle rectangle -> System.out.println("rectangle = " + rectangle);
    case Square square -> System.out.println("square = " + square);
}
```

In the example above, we are checking the type of shape object, and depending on the type, different operations are performed. Note that since we're using a sealed interface, we know exactly which classes could be the type of shape, so we can handle them all explicitly.

**In conclusion, sealed classes and interfaces, together with pattern matching, provide powerful new tools for more explicit, controlled, and flexible design in Java.**  

*** ** * ** ***

Type your email... {#subscribe-email}
