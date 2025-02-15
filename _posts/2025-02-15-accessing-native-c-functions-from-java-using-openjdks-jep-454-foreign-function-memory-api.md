---
title: 'Accessing Native C Functions from Java Using OpenJDK’s JEP 454: Foreign Function & Memory API'
original_url: 'https://bazlur.ca/2023/10/16/accessing-native-c-functions-from-java-using-openjdks-jep-454-foreign-function-memory-api/'
date_published: '2025-02-15T00:00:00+00:00'
date_scraped: '2025-02-15T11:25:26.326853547'
---

![](images/dall-e-2023-10-16-19.12.28-illustration-of-a-venn-diagram.-the-left-circle-represents-java-with-its-coffee-cup-logo-and-some-code-samples.-the-right-circle-represents-c-with-its.png)

Accessing Native C Functions from Java Using OpenJDK's JEP 454: Foreign Function \& Memory API
==============================================================================================

#### Introduction {#introduction}

Java's robustness and cross-platform capabilities have made it a staple in enterprise applications. However, there are scenarios where Java applications need to interact with native libraries written in languages like C or C++. The[Java Native Interface (JNI)](https://en.wikipedia.org/wiki/Java_Native_Interface) has been the traditional solution for such needs, but it comes with its own complexities and performance overheads. OpenJDK's [JEP 454](https://openjdk.org/jeps/454), aims to offer a more straightforward and efficient alternative. Part of [Project Panama](https://openjdk.org/projects/panama/), this API is designed to improve Java's capabilities for interacting with native code. In this article, we'll walk through a simple example to demonstrate how to use JEP 454 to call a C function from a Java program.

It's important to note that this JEP is still in preview mode. To experiment with it, you'll need JDK 22. You can use SDKMan to install JDK 22 on your system easily.

#### What is in JEP 454? {#context-what-is-jep-454-}

Java Enhancement-Proposal (JEP) 454 aims to introduce a **Foreign Function \& Memory API** that simplifies the interaction between Java and native code. The API consists of two main components:

1. **Foreign Function Interface (FFI)**: Enables Java programs to call native functions easily, abstracting much of the boilerplate code required in JNI.
2. **Memory Access API**: Provides a set of tools for interacting with native memory, including features for memory allocation, deallocation, and manipulation of native data structures.

JEP 454 is designed to be highly performant and includes various safety checks to prevent common pitfalls like buffer overflows. It is intended to replace JNI for most use cases, offering a more efficient and safer way to access native libraries and manage native memory.

#### Creating the C Library {#creating-the-c-library}

First, let's create a C program that contains a simple function to add two integers. The code for this is as follows:

```
#include <stdio.h>

int add(int a, int b) {
    return a + b;
}
```

After writing the code, save it in a file named addition.c. To compile this into a shared library, navigate to the directory where the file is located and run the following command:

```
gcc -shared -o libaddition.so -fPIC addition.c
```

This command compiles the C code into a shared library named `libaddition.so`, ready to be accessed by our Java program.

#### Writing the Java Program {#writing-the-java-program}

Next, let's write a Java program that uses the Foreign Function \& Memory API to call the `add` function from our C library. The Java code is as follows:

```
import java.lang.foreign.*;
import java.nio.file.Path;

public class Main {
    void main() {

        try (var arena = Arena.ofConfined()) {
            var lib = SymbolLookup.libraryLookup(Path.of("libaddition.so"), arena);
            var linker = Linker.nativeLinker();
            var fd = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);
            var addFunc = lib.find("add").get();

            var methodHandle = linker.downcallHandle(addFunc, fd);
            var sum = methodHandle.invoke(1, 2);
            System.out.println("sum = " + sum);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
```

Replace `"libaddition.so"` with the actual path to the compiled C library.

#### Running the Java Program {#running-the-java-program}

To compile and run the Java program, use the following command:

```lang-bash
java --source 22 --enable-preview Main.java
```

If everything is set up correctly, the output should display:

    sum = 3

#### Conclusion {#conclusion}

OpenJDK's JEP 454 offers a promising alternative to JNI for interacting with native code. By providing a simpler, safer, and more efficient API, it has the potential to revolutionize how Java developers work with native libraries and memory.  

*** ** * ** ***

Type your email... {#subscribe-email}
