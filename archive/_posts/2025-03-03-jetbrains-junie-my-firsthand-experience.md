---
title: 'JetBrains Junie: My Firsthand Experience'
original_url: 'https://bazlur.ca/2025/03/03/jetbrains-junie-my-firsthand-experience/'
date_published: '2025-03-03T00:00:00+00:00'
date_scraped: '2025-06-18T01:15:42.830591141'
tags: ['personal', 'tools']
featured_image: images/gemini-generated-image-aczqicaczqicaczq.jpeg
---

![](images/gemini-generated-image-aczqicaczqicaczq.jpeg)

JetBrains Junie: My Firsthand Experience
========================================

LLMs are taking over the world---almost literally. Every day, we hear about new models popping up, pushing the boundaries of what AI can do. While some worry about it, others welcome it with open arms. The truth is that AI will inevitably take over certain tasks, much like cars replaced by horse-drawn carriages. But that doesn't mean we'll all be useless. Instead, it means we'll have tools that enhance our productivity and free us from mundane tasks.

As a developer, I'm always on the lookout for anything that can streamline my workflow---something that automates repetitive coding tasks and reduces the time I spend on things that don't necessarily require deep thinking. Let's be honest: not every line of code we write is groundbreaking. A lot of it is boilerplate. And AI is proving to be a fantastic assistant in handling those parts.

I've been experimenting with several AI tools lately---ChatGPT, Gemini, Anthropic's Claude, and many more. I also have access to GitHub Copilot and JetBrains AI Assistant. Both are excellent for auto-completing code and offering contextual suggestions. You can highlight a chunk of code and ask for improvements, especially in UI design. But they're not fully autonomous.

But what if we had an AI agent that could autonomously take on entire tasks? One that I could point to my project and instruct to implement a feature. The agent would not only generate the code but also run tests while I brew my coffee.

Well, that's exactly what I discovered with JetBrains' new AI tool---Junie EAP.

I've been using it for a while now, and in this post, I want to share two specific use cases where it significantly enhanced my workflow.

### Building a Ramadan Awareness Website

As Ramadan approached, I realized that while Muslims worldwide observe the month-long fast, many people---especially in the West---are still unfamiliar with its significance. That got me thinking: why not build a website to raise awareness?

Now, building a website from scratch is no small feat, especially for someone who isn't a front-end expert. So, I turned to Junie. I already had all the content written in the`content.md` file, and I gave it this prompt something like the following, not exactly, as I don't remember:
> "Create a website about Ramadan to help my non-Muslim friends understand its significance. The content is in the `content.md` file---read it and use it for the site. The design should reflect Islamic art and culture, be responsive, visually appealing, and built as a single-page application using React."

Of course, Junie didn't complete the whole thing in one go. It required multiple follow-ups, refinements, and tweaks. However, it did an excellent job laying out the structure, creating reusable components, and producing a functional website. What would have taken me a week or more to build manually, I completed in a single day.

![](images/screenshot-2025-03-03-at-7.42.31-am.png)

That's a huge time saver.
> Checkout the website: **<https://ramadan-facts.onrender.com/>**   
>
> You can scroll to see the whole website.

Of course, it wasn't without hiccups. At one point, a loading dialogue made the entire page disappear. As someone who isn't deeply familiar with CSS quirks, I struggled to fix it, and Junie wasn't able to troubleshoot it effectively. But despite these minor setbacks, the tool proved invaluable.

### Preparing for DevNexus

I'll be speaking at DevNexus this week. For my demo, I built multiple projects that showcased step-by-step implementations with LangChain4j and Jakarta EE.

While I'm comfortable with backend development, frontend work is another story. I wanted to build a chatbot interface with a bit of animations and configure the LLM parameters via the UI. So I wouldn't have to modify the code whenever I wanted to adjust settings.

Again, Junie came to the rescue. It helped me craft a sleek UI using Jakarta Faces with some advanced CSS tricks. The result? A fully functional chatbot interface with dynamic configuration settings---all done in a fraction of the time it would have taken me otherwise.

![](images/screenshot-2025-03-03-at-7.08.21-am.png)

Also, I wanted to update the README. Well, who wants to write it manually when you have Junie? So I gave it this instruction:
> "Can you scan the repository, identify key components, and update the README, including setup instructions, usage details, and any relevant documentation? Also, include a screenshot from the images folder in the README."

![](images/screenshot-2025-03-03-at-7.13.43-am.png)

It did an excellent job. Although I wanted all the screenshots, I asked to add one screenshot to the prompt, but it understood.
> Checkout: <https://github.com/rokon12/llm-jakarta>

### In Closing...

It turns out these tools, like Junie, can automate many coding tasks, create tests, and run them autonomously, and when they fail, it goes on to check the error, fix it, and run them again, but they still need human oversight. They struggle with complex issues, and when they hit a roadblock, I must step in.

The key takeaway? Don't rely on AI mindlessly. Instead, use it as a powerful assistant to handle the tedious parts so you can focus on the creative, high-value aspects of your work.

And that, to me, is an exciting future.

### A Note of Thanks

A huge thank you to JetBrains for allowing me to access Junie EAP. Those who are interested, check this page: <https://www.jetbrains.com/junie/>  

*** ** * ** ***

---

📬 **Stay Updated**: Subscribe to my newsletter at [bazlur.substack.com](https://bazlur.substack.com/) for more articles on:
- ☕ Java & all the new features coming along
- 🧵 Concurrency & Virtual Threads
- 🧠 LLMs, LangChain4j & AI Integration
- 🚀 Quarkus, Spring & Jakarta EE
