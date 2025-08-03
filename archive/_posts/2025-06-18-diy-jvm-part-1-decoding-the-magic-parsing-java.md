---
title: 'DIY JVM Part 1: Decoding the Magic – Parsing Java'
original_url: 'https://bazlur.ca/2025/02/21/diy-jvm-part-1-decoding-the-magic-parsing-java/'
date_published: '2025-06-18T00:00:00+00:00'
date_scraped: '2025-06-18T01:15:48.115349287'
---

![](images/dall-e-2025-02-21-00.27.42-a-close-up-shot-of-a-computer-terminal-displaying-a-hex-dump-of-a-java-.class-file.-the-magic-number-0xcafebabe-is-prominently-visible-at-the-start-of.webp)

DIY JVM Part 1: Decoding the Magic -- Parsing Java
==================================================

It's been about 15 years since I last touched C programming. Back in university, I remember writing a small C program for an assignment---just a simple parser that could scan a C source file and check if its structures were correctly formed. It was nothing fancy, just a mundane academic exercise, and to be honest, I never thought much about it afterward. That was the last time I wrote anything significant in C.

Since then, my career has been all about Java and JVM-based languages. C had faded into the background, a relic of my early programming days. But recently, I found myself feeling a bit nostalgic. C is often called the "mother of all programming languages," and I started wondering---what would it be like to revisit it now, with all the knowledge and experience I've accumulated?

Of course, jumping back into C just for the sake of it didn't make much sense. I wasn't looking to relearn pointers and memory management from scratch. I wanted a project---something meaningful, something that would bridge my JVM expertise with this old friend from my past. And then it hit me: what if I built a barebones JVM in C? Nothing too ambitious---just a minimal interpreter that could read and execute Java class files. That way, I could explore the internals of the JVM while also refreshing my C skills.

And so, this DIY experiment began.

### **Understanding Java .class Files**

When you compile a Java program, it produces a .class file---essentially a binary representation of Java bytecode. This file follows a strict format defined in the Java Virtual Machine Specification. At its core, it's a structured stream of bytes, each part serving a specific purpose. If you've ever wondered what makes a .class file valid, here's a quick breakdown:

* **Magic Number (4 bytes)** : Every valid .class file starts with 0xCAFEBABE (big-endian format). If this signature doesn't match, the JVM rejects the file.
* **Minor \& Major Version (2 + 2 bytes)** : Indicates the Java version the file was compiled for. Java 8, for instance, has a major version of 52.
* **Constant Pool Count (2 bytes)** : This tells us how many entries exist in the constant pool---a table of constants (strings, numbers, method references, etc.) used throughout the class file.
* **Constant Pool (variable size)** : This is where the JVM stores symbolic references to class names, method names, field names, and other constants.
* **Access Flags (2 bytes)** : Specifies whether the class is public, final, abstract, or an interface.
* **This Class (2 bytes)** : A reference to the constant pool entry that holds the fully qualified name of the class.
* **Super Class (2 bytes)** : Similar to This Class, but for the superclass. If the class extends another class, this field points to it; otherwise, it's java.lang.Object.
* **Interfaces, Fields, Methods, and Attributes** : These define what the class implements, the variables it declares, the methods it contains, and additional metadata like annotations and debugging information.

For example, if we take a hexdump of a .class file, it will look like this:

![](images/screenshot-2025-02-21-at-12.55.41-am.png)

As you can see, it is just a stream of bytes, and the first 4 bytes are the magic number, cafe babe.

We don't need to implement everything for our minimal JVM---just enough to parse and understand a .class file.

### **A Quick C Refresher**

Before diving into the implementation, let's quickly touch on some fundamental C concepts we'll use in our parser.

#### **Preprocessor Directives**

In C, preprocessor directives (which begin with #) are handled before actual compilation. Common ones include:

```
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_CONSTANT_POOL_SIZE 32767
```

* **#include \<stdio.h\>** : Includes the standard I/O functions like printf and fopen.
* **#include \<stdlib.h\>** : Includes general utilities like malloc/calloc and free.
* **#define MAX_SIZE 32767** : Defines a macro, replacing occurrences of **MAX_SIZE** with **32767** before compilation.

#### **Structs and Header Files**

To keep things clean, we'll separate our code into header files **(****.h** **)** and source files **(****.c** **)**. Our header file (**diyjvm.h** ) will contain:

```
typedef struct {
    uint32_t magic;
    uint16_t minor_version;
    uint16_t major_version;
    uint16_t constant_pool_count;
    cp_info *constant_pool;

    uint16_t access_flags;
    uint16_t this_class;
    uint16_t super_class;
    uint16_t interfaces_count;

    uint16_t fields_count;  // We'll skip storing the fields themselves for now
    uint16_t methods_count;
    method_info *methods;
} ClassFile;
```

We define data structures using struct and union keywords in C. It's like class, but I guess it's less classy or more!

It will also define constants like:

```
#define JAVA_MAGIC 0xCAFEBABE
#define MAX_STRING_LENGTH 65535

// Constant pool tags
#define CONSTANT_Class               7
#define CONSTANT_Fieldref            9
#define CONSTANT_Methodref           10
#define CONSTANT_InterfaceMethodref  11
#define CONSTANT_String              8
#define CONSTANT_Integer             3
#define CONSTANT_Float               4
#define CONSTANT_Long                5
#define CONSTANT_Double              6
#define CONSTANT_NameAndType         12
#define CONSTANT_Utf8                1
```

And function prototypes:

```
ClassFile *read_class_file(const char *filename);
void free_class_file(ClassFile *cf);
```

To make debugging easier, we use the following macro:

```
#define DEBUG_PRINT(fmt, ...)                                \
    do {                                                     \
        if (debug_mode) {                                    \
            fprintf(stderr, "[DEBUG] " fmt, ##__VA_ARGS__);  \
        }                                                    \
    } while (0)
```

This macro allows us to print debug messages when `debug_mode` is enabled. `__VA_ARGS__` is a preprocessor macro that allows you to create variadic functions in C. Variadic functions are functions that can accept a variable number of arguments. If `debug_mode` is `false`, the macro does nothing.

By including diyjvm.h in our source files, we ensure we have access to these definitions.  

### Memory Management in C

Another important part of C is that once you allocate memory, it's your responsibility to clean it up---no garbage collector here. Forgetting to free memory can lead to leaks, which may slow down or crash your program over time.

A macro like this helps handle free memory:

```
#define SAFE_FREE(p)            \
    do {                        \
        if ((p) != NULL) {      \
            free(p);            \
            (p) = NULL;         \
        }                       \
    } while (0)
```

### Handling Endianness in .class File Parsing

One critical aspect of parsing Java class files in C is handling endianness. Java class files use **big-endian** format, meaning the most significant byte comes first. However, many modern systems, particularly x86-based machines, use **little-endian** format, where the least significant byte comes first. If we were to read multi-byte values directly, they would be interpreted incorrectly on little-endian systems.

To handle this, I need to write some function as follows:

```
static uint32_t read_u4(FILE *fp, bool *ok) {
    uint32_t value = 0;
    if (!safe_fread(&value, 4, 1, fp)) {
        *ok = false;
        return 0;
    }
    return __builtin_bswap32(value); // Convert from big-endian
}
```

he `read_u4` function reads four bytes from the file and stores them in `value`. However, since `value` is stored in the system's native endianness, we need to ensure that it is properly converted. The `__builtin_bswap32` function swaps the byte order, converting from big-endian to little-endian where necessary. This ensures that the parsed values are correctly interpreted regardless of the underlying architecture.

### Parsing a `.class` File

The `read_class_file` function is responsible for reading a Java class file and extracting its structural elements. It performs the following tasks:

* Opens the specified class file for reading.
* Reads and verifies the magic number (`0xCAFEBABE`).
* Extracts the minor and major version numbers, ensuring they are within the supported range.
* Parses the constant pool, allocating memory dynamically for its entries.
* Reads access flags, class references, and superclass details.
* Extracts interface and field metadata.
* Skips over field and method attributes, ensuring the correct structure is maintained.
* Allocates space for methods and parses their details, including code attributes.

Parts of this function are here:

```
ClassFile *read_class_file(const char *filename) {
    DEBUG_PRINT("Opening class file: %s\n", filename);

    FILE *file = fopen(filename, "rb");
    if (!file) {
        char error_msg[256];
        snprintf(error_msg, sizeof(error_msg), "Failed to open class file '%s'.", filename);
        ERROR_AND_CLEANUP(error_msg, { /* no cleanup needed here */ });
    }

    bool ok = true;
    ClassFile *cf = malloc(sizeof(ClassFile));
    if (!cf) {
        ERROR_AND_CLEANUP("Out of memory allocating ClassFile.", {
            fclose(file);
        });
    }
    memset(cf, 0, sizeof(*cf)); // zero out structure

    // Read magic
    cf->magic = read_u4(file, &ok);
    DEBUG_PRINT("Read magic number: 0x%08X\n", cf->magic);
    if (!ok || cf->magic != JAVA_MAGIC) {
        char error_msg[256];
        snprintf(error_msg, sizeof(error_msg),
                 "Invalid or missing magic number in '%s'.", filename);
        ERROR_AND_CLEANUP(error_msg, {
            free_class_file(cf);
            fclose(file);
        });
    }
    DEBUG_PRINT("Magic number verified successfully\n");

    // Read minor/major version
    cf->minor_version = read_u2(file, &ok);
    cf->major_version = read_u2(file, &ok);
    if (!ok) {
        ERROR_AND_CLEANUP("Could not read version numbers.", {
            free_class_file(cf);
            fclose(file);
        });
    }

    if (cf->major_version < 45 || cf->major_version > 69) {
        ERROR_AND_CLEANUP("Unsupported class file version.", {
            free_class_file(cf);
            fclose(file);
        });
    }

    // Read constant pool count
    cf->constant_pool_count = read_u2(file, &ok);
    DEBUG_PRINT("Constant pool count: %d\n", cf->constant_pool_count);
    if (!ok || cf->constant_pool_count > MAX_CONSTANT_POOL_SIZE) {
        ERROR_AND_CLEANUP("Invalid constant pool count.", {
            free_class_file(cf);
            fclose(file);
        });
    }

    cf->constant_pool = (cp_info *) calloc(cf->constant_pool_count, sizeof(cp_info));
    if (!cf->constant_pool) {
        ERROR_AND_CLEANUP("Out of memory allocating constant pool.", {
            free_class_file(cf);
            fclose(file);
        });
    }

....
.....
}
```

### **Project Structure**

Our project follows a simple layout:

```
diyjvm/
├── include/
│   └── diyjvm.h
├── src/
│   └── main.c
└── test/
    └── HelloWorld.class
```

* **include/** : Holds header files.
* **src/** : Contains source code.
* **test/** : Includes sample .class files for testing.

### **Source Code:**

The GitHub repository can be found here: <https://github.com/rokon12/diyjvm/>

### **Compiling and Running**

To compile the program:

```
gcc -DDEBUG -Wall -Wextra -I./include src/main.c -o diyjvm
```

Then, to run it:

```
./diyjvm test/HelloWorld.class
```

This should print something like:

```
Class file: test/HelloWorld.class
Magic: 0xCAFEBABE
Version: 65.0
Constant pool entries: 29
Methods: 2
```

For debugging:

```
./diyjvm -d test/HelloWorld.class
```

This will output more detailed logs about how the file is being parsed.  

```
[DEBUG] Initializing diyJVM...
[DEBUG] Opening class file: test/HelloWorld.class
[DEBUG] Read magic number: 0xCAFEBABE
[DEBUG] Magic number verified successfully
[DEBUG] Constant pool count: 29
[DEBUG] Reading constant pool entry with tag: 10
[DEBUG] Reading constant pool entry with tag: 7
[DEBUG] Reading constant pool entry with tag: 12
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 9
[DEBUG] Reading constant pool entry with tag: 7
[DEBUG] Reading constant pool entry with tag: 12
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 8
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 10
[DEBUG] Reading constant pool entry with tag: 7
[DEBUG] Reading constant pool entry with tag: 12
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 7
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Reading constant pool entry with tag: 1
[DEBUG] Methods count: 2
[DEBUG] Method[0]: access=0x0001, name_index=5, desc_index=6, attr_count=1
[DEBUG]  -> Found Code attribute
[DEBUG] Method[0], Code attribute, Sub-attribute 0: name_index=24, length=6
[DEBUG] Method[1]: access=0x0009, name_index=25, desc_index=26, attr_count=1
[DEBUG]  -> Found Code attribute
[DEBUG] Method[1], Code attribute, Sub-attribute 0: name_index=24, length=10
Class file: test/HelloWorld.class
Magic: 0xCAFEBABE
Version: 65.0
Constant pool entries: 29
Methods: 2
[DEBUG] Cleaning up diyJVM...
```

### **Wrapping Up**

This is just the beginning. In this first part, we've laid the groundwork---understanding Java class files, refreshing some C fundamentals, and setting up our project. The real fun begins as we start parsing these files in earnest.

Next time, we'll get our hands dirty with more features. Stay tuned!

*** ** * ** ***

Type your email... {#subscribe-email}
