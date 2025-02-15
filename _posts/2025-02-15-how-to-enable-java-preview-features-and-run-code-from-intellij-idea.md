---
title: 'How to Enable Java Preview Features and Run Code from IntelliJ IDEA'
original_url: 'https://bazlur.ca/2022/05/08/how-to-run-project-loom-from-intellij-idea/'
date_published: '2025-02-15T00:00:00+00:00'
date_scraped: '2025-02-15T11:30:01.632161243'
---

![](images/idea-overview-5-1-2x.webp)

How to Enable Java Preview Features and Run Code from IntelliJ IDEA
===================================================================

[JEP 425: Virtual Threads (Preview)](https://openjdk.java.net/jeps/425) has been proposed recently. It has been a long-awaited feature in Java. I wanted to give it a try. So I download the [early release](https://jdk.java.net/loom/) of JDK which has the [project loom](https://wiki.openjdk.java.net/display/loom/Main) in it. However, it is under preview.

The following snippet was pretty much my first program written for testing virtual threads.

```
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread.startVirtualThread(() -> {
            System.out.println("Hello from virtual thread");
        }).join();
    }
}
```

It is so simple that I could just run it in the command line using the [source code launcher](https://openjdk.java.net/jeps/330) :

```
java --enable-preview --release 19 Main.java
```

However, it needed a bit of [yak shaving](https://en.wiktionary.org/wiki/yak_shaving). I needed to download the JDK, extract the tarball, set the java home, etc. I manage multiple JDKs using [SDKMAN](https://sdkman.io/); it doesn't have it since it's still in early access release. So I had to let SDKMAN know it manually.

Then I figured, maybe, perhaps an IDE could help me here. So I opened my favourite IDE, which happens to be [IntelliJ IDEA](https://www.jetbrains.com/idea/). I created a project and set up the JDK using the following window-

![](images/screen-shot-2022-05-08-at-4.59.40-am.png)

Then when I tried to run, it didn't allow me to run the code since the virtual thread was still in preview. Here are the steps I had to go through in IntelliJ IDEA.

First, you need to go preference, and then **Build, Execution, Deployment** and then Select Java Compiler.

![](images/screen-shot-2022-05-08-at-5.03.41-am.png)

At the bottom, there is a box named the additional command line parameter. Add the following line there-

```
--enable-preview
```

And then go to the run configuration. Select the modify options and Mark the Add VM options.

![](images/screen-shot-2022-05-08-at-5.04.39-am.png)

You need to add `--enable-preview `there as well.

![](images/screen-shot-2022-05-08-at-5.11.43-am.png)

That's it.

Now you can run the project loom from IntelliJ IDEA.

*** ** * ** ***

Type your email... {#subscribe-email}
