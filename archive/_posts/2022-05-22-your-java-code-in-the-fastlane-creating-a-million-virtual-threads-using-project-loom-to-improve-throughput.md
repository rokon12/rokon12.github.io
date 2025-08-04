---
title: 'Your Java Code in the Fastlane: Creating a Million Virtual Threads Using Project Loom to Improve Throughput'
original_url: 'https://bazlur.ca/2022/05/22/your-java-code-in-the-fastlane-creating-a-million-virtual-threads-using-project-loom-to-improve-throughput/'
date_published: '2022-05-22T00:00:00+00:00'
date_scraped: '2025-02-15T11:29:41.892917026'
tags: ['concurrency', 'java', 'performance']
---

Your Java Code in the Fastlane: Creating a Million Virtual Threads Using Project Loom to Improve Throughput
===========================================================================================================

**Abstract**

Project Loom introduces virtual threads, lightweight threads that aim to dramatically reduce the effort of writing, maintaining, and monitoring high-throughput concurrent applications with the Java platform.

We need threads to achieve high throughput. However, threads are not cheap and are limited in number. To get around this problem, various alternatives such as the reactive programming style have emerged. These techniques bypass creating a lot of threads at the expense of more difficult debugging. This makes developers grumpy. However, with virtual threads, we get the best of both worlds, cheap, lightweight threads and easy debugging, which would make developers happy again.

This talk will explore what virtual threads are, how they are implemented, how they solve our modern problems and what, if any, shortcomings there are.

**Status: Booked July 19, 2022, [PhillyJUG (USA)](https://www.meetup.com/PhillyJUG/events/285970065/)**  

*** ** * ** ***

---

📬 **Stay Updated**: Subscribe to my newsletter at [bazlur.substack.com](https://bazlur.substack.com/) for more articles on Java, Software Architecture, and Technology.
