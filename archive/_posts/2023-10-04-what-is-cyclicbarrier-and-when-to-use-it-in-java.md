---
title: 'What is CyclicBarrier and When to Use It in Java?'
original_url: 'https://bazlur.ca/2023/10/04/what-is-cyclicbarrier-and-when-to-use-it-in-java/'
date_published: '2023-10-04T00:00:00+00:00'
date_scraped: '2025-02-15T11:25:36.901497055'
tags: ["concurrency", "ai", "java", "tutorial", "tools"]
featured_image: images/7c64ca58-0810-4373-973a-a27c44ece1bb.jpeg
---

![](images/7c64ca58-0810-4373-973a-a27c44ece1bb.jpeg)

What is CyclicBarrier and When to Use It in Java?
=================================================

In Java's concurrency API, CyclicBarrier is another kind of synchronizer, similar to CountDownLatch. It enables multiple threads to wait for each other at a predefined execution point before resuming work. For example, consider a financial application that performs complex risk assessments for various portfolios. The reviews could involve multiple steps like data gathering, calculations and report generation.{#ember1331}

In this scenario, a CyclicBarrier can be used to ensure that all steps are synchronized across different threads. Below is a more detailed example illustrating this concept.

```
package ca.bazlur.concurrency101;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CyclicBarrierDemo {
    public static void main(String[] args) throws InterruptedException {
        final int numberOfPortfolios = 3;
        Runnable barrierAction = ()
                -> System.out.println("All portfolios have completed the current step. " +
                "Proceeding to the next step.");

        var barrier = new CyclicBarrier(numberOfPortfolios, barrierAction);
        var random = new Random();
        IntStream.rangeClosed(1, numberOfPortfolios)
                .forEach(i -> Thread.startVirtualThread(
                        new PortfolioTask(("Portfolio " + i),
                                barrier, random.nextLong(1000))));

        // Keeping main thread alive to allow `PortfolioTask` threads to complete
        // Java Virtual Threads are daemon threads, they will stop when no other 
        // non-daemon threads (main in this case) are running.
        // So when the main thread finishes, these Virtual Threads could be stopped 
        // before completing their tasks.
        TimeUnit.SECONDS.sleep(30);
        System.out.println("Hope by now all work is done!");
    }
}

record PortfolioTask(String portfolioName,
                     CyclicBarrier barrier,
                     Long sleepTime) implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(portfolioName + " is gathering data...");
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            barrier.await();

            System.out.println(portfolioName + " is calculating risk...");
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            barrier.await();

            System.out.println(portfolioName + " is generating the report...");
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            barrier.await();

            System.out.println(portfolioName + " risk assessment is complete.");
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

Output of the code:

```
Portfolio 1 is gathering data...
Portfolio 3 is gathering data...
Portfolio 2 is gathering data...
All portfolios have completed the current step. Proceeding to the next step.
Portfolio 3 is calculating risk...
Portfolio 2 is calculating risk...
Portfolio 1 is calculating risk...
All portfolios have completed the current step. Proceeding to the next step.
Portfolio 3 is generating the report...
Portfolio 1 is generating the report...
Portfolio 2 is generating the report...
All portfolios have completed the current step. Proceeding to the next step.
Portfolio 3 risk assessment is complete.
Portfolio 2 risk assessment is complete.
Portfolio 1 risk assessment is complete.
Hope by now all work is done!
```

In this example of a financial portfolio risk assessment application, a CyclicBarrier effectively synchronizes multiple threads through different steps. This ensures that all portfolios complete each step before moving on to the next, maintaining the integrity and accuracy of the risk assessments.{#ember2835}

Besides, a significant benefit of CyclicBarrier over CountDownLatch is reusability. It can be reused after the waiting threads are released in its barrier point. That's why it's named '**Cyclic**' because it can be used in a cyclic manner, i.e., in repeated events/operations.{#ember2836}

In the code, we have reused CyclicBarrier in multiple steps.{#ember2837}

*** ** * ** ***

Type your email... {#subscribe-email}
