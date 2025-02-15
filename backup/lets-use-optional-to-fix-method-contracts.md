---
title: 'Let’s Use Optional to Fix Method Contracts'
original_url: 'https://bazlur.ca/2021/11/10/lets-use-optional-to-fix-method-contracts/'
date_scraped: '2025-02-15T09:10:38.887401097'
---

Let's Use Optional to Fix Method Contracts
==========================================

A method is a contract: when we define one, we put thought into it. We specify parameters with their type and also a return type. When we invoke a method, we expect it to behave according to the contract. If it doesn't, it's a violation of the contract.

We have been dealing with such violations all the time. We invoke a method with its proper arguments and we get a return. However, sometimes we end up getting a null, which is, in fact, a clear violation. We shouldn't accept such a violation. If a method cannot return the value of the specified type, it should mention that in the method signature, the method may or may not be returning the value you are expecting. If we know it from the method signature, we then write our code accordingly.

Now the question is, how do we define such a contract in a method signature? Well, that's where Optional comes into play. Set your return type as Optional. Optional is a mystery box, a wrapping paper: it may or may not contain the value. When we specify that in a method signature, we assume that the box might be empty. Let's see an example:

```
public static Optional<Book> findBookByName(String name) {
  return books.stream()
          .filter(book -> book.title().equalsIgnoreCase(name))
          .findAny();
}
```

The method above specified Optional as a return type. It may return the book that I'm looking for or may not. I'm aware of this, and I can deal with it when I invoke it. For example:

```
var bookOpt = findBookByName("Java Programming");
if (bookOpt.isPresent()) {
    var book = bookOpt.get();
    var releasedYear = book.releasedYear();
    System.out.println("Java Programming was published in " + releasedYear);
} else {
    System.out.println("Book was not found");
}
```

Or we can do the same thing with the functional construct, e.g:

```
findBookByName("Java Programming")
.map(Book::releasedYear)
.ifPresentOrElse((releasedYear)
        -> System.out.println("Java Programming was published in " + releasedYear),
() -> System.out.println("Book was not found"));
```

The bottom line is, we should fix our method contract and use optional rather than returning null.  

*** ** * ** ***

### Discover more from A N M Bazlur Rahman

Subscribe to get the latest posts sent to your email.  
Type your email... {#subscribe-email}

Subscribe {#subscribe-submit}
