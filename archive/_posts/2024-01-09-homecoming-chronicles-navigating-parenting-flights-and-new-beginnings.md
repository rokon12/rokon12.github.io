---
title: 'Homecoming Chronicles: Navigating Parenting, Flights, and New Beginnings'
original_url: 'https://bazlur.ca/2024/01/09/homecoming-chronicles-navigating-parenting-flights-and-new-beginnings/'
date_published: '2024-01-09T00:00:00+00:00'
date_scraped: '2025-02-15T11:25:20.76120587'
tags: ["java", "concurrency", "ai", "performance", "tools"]
featured_image: images/1704519735509.png
---

![](images/1704519735509.png)

Homecoming Chronicles: Navigating Parenting, Flights, and New Beginnings
========================================================================

Toronto usually blanketed in snow, hasn't seen much this winter yet---El Nino effect. I was a bit surprised as well.{#ember735}

Amidst this, I realized it's been ages since my last newsletter. A lot of events unfolded in the interim. The biggest thing? I went back to my homeland with my little baby girl, who is only 10 months old. It's been four years since I was last there. My parents hadn't seen her in person before. It could have been a surprise for them, but because of video calls and stuff, they'd seen her on the screen but couldn't hold her. That was really sad for both our parents and us.{#ember736}

Embarking on such a trip is no small feat. It took a long time to get ready. We flew from Toronto to Dubai first, which took about 13 hours. Then, we waited in Dubai for 5 hours before flying again to Dhaka. Long flights can fray anyone's nerves, but with a baby who's a stranger to the concept of sleep in the clouds, it's a whole new level of challenge. The return flight was even longer.{#ember737}

Our 40 days in Bangladesh flew by really quickly. We thought Dhaka would be a bit chilly in December, but it was warm, just like summer in Toronto. It was amazing spending time with our parents after so long. We had so much to catch up on. I still wonder if it was all just a dream, only to wake up in my own bed in Toronto. It's strange how time flies so fast when you're having a good time.{#ember738}

In between, life's gears kept turning. I've embarked on a new adventure career-wise, joining DNAStack as a **Staff Software Developer.** The world of genetic research and precision medicine is a thrilling frontier, and I'm eager to contribute as much as I can to advancements that could redefine human life's ease and quality.{#ember739}

Since the last newsletter, I haven't written much. But I did do some cool stuff with Java. Here are some highlights:{#ember740}

*** ** * ** ***

* **JEP 423: Introducing Region Pinning to G1 Garbage Collector in OpenJDK:** After its review concluded, JEP 423, Region Pinning for G1, has been integrated into JDK 22. This JEP proposes to reduce GC latency by implementing region pinning for the G1 garbage collector. This will extend G1 so that arbitrary regions may be pinned during both major and minor collection operations, so that disabling the garbage collection process may be avoided while implementing JNI.... [Read further\^](https://www.infoq.com//news/2023/12/region-pinning-to-g1-gc)
* **Stream API Evolution: a Closer Look at JEP 461's Stream Gatherers:** After its review concluded, JEP 461, Stream Gatherers (Preview), has been completed for JDK 22. This JEP proposes to enhance the Stream API to support custom intermediate operations. "This will allow stream pipelines to transform data in ways that are not easily achievable with the existing built-in intermediate operations."... [Read further\^](https://www.infoq.com//news/2023/12/stream-api-evolution)
* **JEP 457: Streamlining Java Development with the Class-File API (2023-12-06):** JEP 457, Class-File API (Preview), has been integrated into JDK 22, proposing a new API for parsing, generating, and transforming Java class files. This API will initially replace ASM within the JDK with plans for a public API. Goetz, the Java language architect at Oracle, described ASM as outdated and provided details on the API's evolution.... [Read further\^](https://www.infoq.com//news/2023/12/jep-457-new-class-file-api)
* **Javet 3.0.2 Released: Bridging Java and JavaScript with Enhanced Features:** Javet, a fusion of Java and V8 (JAVa + V + EighT), has recently released its version 3.0.2, marking a significant advancement in embedding Node.js and V8 in Java. This version includes Node.js v20.10.0 and V8 v12.0.267.8, highlighting the project's commitment to staying current with the latest developments in these technologies.... [Read further\^](https://www.infoq.com//news/2023/12/javet-302-released)
* **Foreign Function \& Memory API to Bridge the Gap between Java and Native Libraries** : After its review concluded, JEP 454, Foreign Function \& Memory API has been promoted from Targeted to Integrated for JDK 22. This JEP proposes to finalize this feature after two rounds of incubation and three rounds of preview. The API aims to replace traditional, complex methods like JNI, offering a more efficient and secure approach.... [Read further\^](https://www.infoq.com//news/2023/10/foreign-function-and-memory-api)
* **How to Diagnose and Mitigate Pinning in Java's Virtual Thread Execution:** In the context of virtual threads, pinning refers to the condition where a virtual thread is "stuck" to its carrier thread (the platform thread on which it runs).... [Read further\^](https://foojay.io/today/how-to-diagnose-and-mitigate-pinning-in-javas-virtual-thread-execution/)
* **Web Crawling in Java: A Tale of Classical Threads and Virtual Threads:** A compelling narrative around web crawling in Java, contrasting classical threads with their newer counterpart: virtual threads.... [Read further\^](https://foojay.io/today/web-crawling-in-java-a-tale-of-classical-threads-and-virtual-threads/)
* **Exploring the Impact of Stack Size on JVM Thread Creation: A Myth Debunked:** Does stack size have an impact on the number of native threads that can be created in a JVM environment?... [Read further\^](https://foojay.io/today/exploring-the-impact-of-stack-size-on-jvm-thread-creation-a-myth-debunked/)
* **Embracing Modernity: A Comprehensive Look at Sealed Classes, Pattern Matching, and Functional Paradigms in Java** : Let's examine the principles and practical applications of Sealed Classes and pattern matching.... [Read further\^](https://foojay.io/today/embracing-modernity-a-comprehensive-look-at-sealed-classes-pattern-matching-and-functional-paradigms-in-java/)
* [**Java and the String Odyssey: Navigating Changes from JDK 1 to JDK 21:**](https://www.unlogged.io/post/java-and-the-string-odyssey-navigating-changes-from-jdk-1-to-jdk-21)Explore Java's string evolution from immutability in JDK 1 to the cutting-edge String Templates in JDK 21.
* [Java Concurrency Unlocked: A Comparative Guide to Synchronization :](https://www.unlogged.io/post/java-concurrency-unlocked-a-comparative-guide-to-synchronization-tools)This blog post discusses different lock mechanisms in Java for managing concurrency.

### Contributions to Reviews and Interviews {#ember742}

* [Architecting with Java Persistence: Patterns and Strategies](https://www.infoq.com/articles/architecting-java-persistence-patterns-and-strategies) Discover Java persistence patterns: Driver, Mapper, DAO, Active Record, Repository. Balance layers and optimize data flow.
* [Helidon 4 Adopts Virtual Threads: Explore the Increased Performance and Improved DevEx](https://www.infoq.com/articles/helidon-4-adopts-virtual-threads) This article explores Helidon 4's adoption of Java 21's virtual threads from Project Loom, simplifying coding and boosting performance for microservice development.
* **Unlocking Java Wisdom: A Conversation with Oracle ACE Simon Martinelli** : In a recent insightful interview, Simon Martinelli, an Oracle ACE associate and veteran Java developer, shares his career experiences, software development philosophies, and views on mentoring. With over two decades in the industry, Martinelli offers a perspective that combines the ...... [Read further\^](https://foojay.io/today/unlocking-java-wisdom-a-conversation-with-oracle-ace-simon-martinelli/)

### Conferences: {#ember745}

Among all these, I'm thrilled to announce that one of my talks has been accepted at [jChampionsConference](https://jchampionsconf.com/schedule.html#rahmanCard) and two talks at [ConFoo](https://confoo.ca/en/speaker/a-n-m-bazlur-rahman-1).{#ember746}

I hope this compilation will keep you engaged for a while. Rest assured, I'll be back next month with more intriguing content.{#ember748}

Until then, happy reading! And thank you for being a part of this journey with me.{#ember749}  

*** ** * ** ***

Type your email... {#subscribe-email}
