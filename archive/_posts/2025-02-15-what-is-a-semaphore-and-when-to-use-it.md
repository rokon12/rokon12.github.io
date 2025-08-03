---
title: 'What is a semaphore, and when to use it?'
original_url: 'https://bazlur.ca/2023/10/05/what-is-a-semaphore-and-when-to-use-it/'
date_published: '2025-02-15T00:00:00+00:00'
date_scraped: '2025-02-15T11:25:33.872565937'
featured_image: images/b8ae274c-6229-4645-9a20-ef9412595531.jpeg
---

![](images/b8ae274c-6229-4645-9a20-ef9412595531.jpeg)

What is a semaphore, and when to use it?
========================================

In Java's concurrency API, a semaphore is another synchronization tool that simultaneously controls the number of threads accessing a particular resource or section of code. It manages a set of permits; threads must acquire a permit before proceeding. If a permit is available, the thread acquires it and continues execution. If not, the thread is blocked until a permit becomes available or interrupted. The Semaphore class in Java offers two primary methods: acquire() and release().{#ember1009}

Let's assume a database that can only endure ten connections simultaneously. With the advent of Virtual Threads in JDK 21, creating many threads has become relatively easy and cheap. This becomes a double-edged sword for this particular case, as many threads can attempt to connect in this database simultaneously, and this can overwhelm the database.{#ember1010}

Here is our MockDatabase class:

```
public class MockDatabase {
    public MockConnection getConnection() {
        try {
            TimeUnit.MILLISECONDS.sleep(10); // assume it takes 10 ms
            return new MockConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

To avoid overwhelming the database, we can employ a semaphore to limit concurrent access. Below is the modified MockDatabase class with a Semaphore:

```
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MockDatabase {
    private final Semaphore semaphore = new Semaphore(10);

    public MockConnection getConnection() {
        try {
            semaphore.acquire();
            TimeUnit.MILLISECONDS.sleep(10);
            return new MockConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
```

To ensure that the semaphore is effectively limiting the database connections to ten, we can create a JUnit test. The test will utilize an AtomicInteger to count the maximum number of concurrent calls to the getConnection() method and verify that it never exceeds ten.

```
import org.junit.jupiter.api.Test;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MockDatabaseTest {
    @Test
    public void testGetConnection_shouldLimitConcurrentInvocationsToTen() {
        var concurrentCalls = new AtomicInteger(0);
        var maxConcurrentCalls = new AtomicInteger(0);

        var instance = new MockDatabase() {
            @Override
            public MockConnection getConnection() {
                concurrentCalls.incrementAndGet();
                maxConcurrentCalls.set(Math.max(maxConcurrentCalls.get(), concurrentCalls.get()));
                concurrentCalls.decrementAndGet();
                return super.getConnection();
            }
        };

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1000)
                    .forEach(index -> instance.getConnection());
        }

        assertThat(maxConcurrentCalls.get()).isLessThanOrEqualTo(10);
    }
}
```

In conclusion, Java's Semaphore class offers a robust and straightforward mechanism for controlling access to limited resources in a concurrent environment.  

*** ** * ** ***

Type your email... {#subscribe-email}
