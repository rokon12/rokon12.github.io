---
title: 'What is CountDownLatch and How to Use It?'
original_url: 'https://bazlur.ca/2023/10/04/what-is-countdownlatch-and-how-to-use-it/'
date_published: '2023-10-04T00:00:00+00:00'
date_scraped: '2025-02-15T11:25:39.358357387'
tags: ['concurrency', 'java', 'tutorial']
featured_image: images/f8ce2ee3-e5e4-4fc4-91ea-47f05eb997b7.jpeg
---

![](images/f8ce2ee3-e5e4-4fc4-91ea-47f05eb997b7.jpeg)

What is CountDownLatch and How to Use It?
=========================================

In Java's concurrency API, CountDownLatch is a synchronizer that allows one or more threads to wait for a set of operations to complete. Imagine you're developing a server application that relies on various resources to be initialized before it can start processing requests. These resources could be things like:{#ember49}

* Loading configuration files
* Establishing database connections
* Initializing caches
* Starting embedded servers or services

A CountDownLatch is initialized with a given count, representing the number of actions that must occur before the latch is released. Each action decrements this count. When the count reaches zero, all waiting threads are released, and any subsequent invocations of the latch's methods will pass without blocking.{#ember51}

### How to Use CountDownLatch: {#ember52}

The primary methods are await() for waiting and countDown() for decrementing the count.{#ember53}

```
package ca.bazlur.concurrency101;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.createContext("/hello", new GreetingsHandler());

        CountDownLatch latch = new CountDownLatch(4);

        Thread.startVirtualThread(new Task("Load Config", latch));
        Thread.startVirtualThread(new Task("Init DB Connection", latch));
        Thread.startVirtualThread(new Task("Init Cache", latch));
        Thread.startVirtualThread(new Task("Start Embedded Server", latch));

        latch.await();

        System.out.println("All initializations complete. Application is starting...");

        server.start();
    }
}

record Task(String name, CountDownLatch latch) implements Runnable {
    @Override
    public void run() {
        doHeavyLifting();
        System.out.println(name + " has finished.");
        latch.countDown();
    }

    private static void doHeavyLifting() {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GreetingsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "Hello world!";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
```

In this example, we initialize a CountDownLatch with a count of 4. Then, we create four virtual threads. Each task calls countDown() on the latch after it is completed. The main thread waits for these tasks by calling await() on the latch. When the count reaches zero, the message "**All initializations complete. Application is starting...**" is displayed, and we start the server.  

*** ** * ** ***

---

📬 **Stay Updated**: Subscribe to my newsletter at [bazlur.substack.com](https://bazlur.substack.com/) for more articles on Java, Software Architecture, and Technology.
