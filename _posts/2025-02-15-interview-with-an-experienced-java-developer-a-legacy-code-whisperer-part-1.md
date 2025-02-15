---
title: 'Interview with an experienced Java developer – a legacy code whisperer – Part -1'
original_url: 'https://bazlur.ca/2017/10/13/interview-with-an-experienced-java-developer-a-legacy-code-whisperer-part-1/'
date_published: '2025-02-15T00:00:00+00:00'
date_scraped: '2025-02-15T11:31:31.026450186'
---

![](images/interviews.jpeg)

Interview with an experienced Java developer -- a legacy code whisperer -- Part -1
==================================================================================

Once you develop a successful software product, you have to maintain it for a long time. Maintaining software isn't an easy task. If you don't take good care while coding and then gradually improve it, your system is destined to rot. We certainly do not want that to happen.

Today I have got an opportunity to talk to an experienced Java developer who has been working in the industry for over 20 years. He shared a lot of insight from his experience.

Let's read what he has to say --

* Name: **Scott Wierschem**
* Home Page: <http://keepcalmandrefactor.com/>
* LinkedIn: <https://www.linkedin.com/in/scott-wierschem-a25b75/>

Bazlur Rahman: ***Hello Scott, welcome to the [MasterDevSkills.com](https://masterdevskills.com/). How are you doing today?***
> **Scott Wierschem**: Tremendous! How about you?

Bazlur Rahman: ***I'm doing great; in fact really excited about this interview. Let's begin!***
> **Scott Wierschem**: Sounds great!

Bazlur Rahman: ***I know you have been working in the industry for over 20 years, but would you mind telling our readers a bit about yourself, your current position, your work, and what you do?***
> **Scott Wierschem**: Yes, I've been around a while! My first job was working on a control system where everything was rolled "in-house" -- even the relational database! I learned there that the management makes all the differences in a company. Even if you're doing great work, if the management makes poor strategic decisions, the company can fail.
>
> Currently, I do contract work. I support a 20-year-old Java Swing application that is the front end to a database. It was written before most of the standard libraries we have now (like [ORMs](https://en.wikipedia.org/wiki/Object-relational_mapping) and [IOC](https://en.wikipedia.org/wiki/Inversion_of_control) frameworks) were created, so much of it is proprietary.
>
> Since the original author is no longer with the company, I have to dig through the old code a lot. There are no unit tests and any changes can break things in surprising ways.
>
> My current effort is to introduce [Characterization Tests](https://en.wikipedia.org/wiki/Characterization_test) -- a concept I learned from Michael Feathers' book, "[Working Effectively with Legacy Code](https://www.amazon.com/Working-Effectively-Legacy-Michael-Feathers/dp/0131177052)." Basically, you put code coverage tests around a section of code before you refactor it.
>
> Little by little I'm making my world a better place! I really enjoy it!

* *Observation # 1: Strategic Decision is important to be able to succeed, and sometimes it's not in your hand.*
* *Observation # 2: Swing isn't dead yet.*

Bazlur Rahman: ***I'm sure you are doing great; ultimately, what we write today, becomes a legacy after a while unless we take care of it. And obviously, for a long-running project, you will not always have the original author beside you. That's the reality of our software industry, isn't it?***

***I guess that's why you started the project [KeepCalmAndRefactor.com](http://keepcalmandrefactor.com/). Tell us about your motivation behind it.***
> **Scott Wierschem**: Well, it's kind of embarrassing, but for most of my career I've been miserable. I would inherit someone else's disaster and complain about how awful it was, and if I could just rewrite it everything would be great! Then I got the chance to do that and was shocked to find that I couldn't write maintainable code either! All I knew was what I'd learned from previous coding experiences. It was really discouraging.
>
> A few years ago I heard an interview with [Andrea Goulet of Corgibytes](https://www.linkedin.com/in/andreamgoulet/). She talked about how her company loves to work with legacy code and fix it to make it better. She referenced some books and other industry leaders who did this as well. It was like a light shone on me: that's what I could do!
>
> There was a community and tools and techniques for doing what I really wanted to do! I immersed myself into the books, watched countless YouTube videos, and became an active member of the legacy code and software craftsmanship communities. I guess you could say, "***I got religion!***"
>
> It was still frustrating to try and apply these tools and techniques to my project. Since all the industry leaders had been doing it for decades, there was no real education on how to get started.
>
> I thought I would fill that gap. I spent hours -- or days -trying to figure out how to set up [JUnit](http://junit.org/junit5/) on an old [NetBeans](https://netbeans.org/) project. It turns out that it's a few config settings. My goal is to shorten the learning curve to using the best practices on existing projects -- especially ones that haven't been using them in the past.
>
> Also, I have spent much of my career working alone on various projects. When you get stuck, there's no one to ask for help! You learn a lot just by working with others. [KeepCalmAndRefactor.com](http://keepcalmandrefactor.com/) will be a place where people can go to learn the tricks and techniques that industry leaders use to get their work done well. And a place where they can find support for when they get stuck.
>
> Making our code better a little bit at a time. Once you know the tools and techniques, it becomes **fun** and **rewarding** to clean up **legacy code**! I want to share that feeling with everyone!

* *Observation # 3: Rewriting the whole codebase isn't the solution, rather, gradual improvement is what is needed.*
* *Observation # 4: To understand something, you have to study a lot, read books, and watch lectures on YouTube. In general, being **persistence** and **perseverance** are important.*
* **Observation # 5: You will not always get the help needed; rather, you have to find your own solution while struggling. Being able to stick to something is rewarding.**--to be contained--

*** ** * ** ***

Type your email... {#subscribe-email}
