---
layout: default
title: Home
---

# Welcome to Bazlur's Blog Archive

A comprehensive collection of articles on Java, Software Development, and Technology. This archive contains technical articles, tutorials, and insights from my years of experience in software development.

## Recent Posts

{% assign sorted_files = site.static_files | sort: 'modified_time' | reverse %}
{% assign post_count = 0 %}
{% for file in sorted_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' and post_count < 10 %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}

{% assign lines = post_content | newline_to_br | split: '<br />' %}
{% assign title = file.basename | replace: '-', ' ' | capitalize %}

### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})

{% assign post_count = post_count | plus: 1 %}
{% endif %}
{% endfor %}

[View More Posts →](./categories)

## Browse by Category

- [Java Programming](./categories#java-programming) - Core Java, JVM, and Java ecosystem
- [Thread Programming](./categories#thread-programming) - Concurrency and parallel programming
- [Software Development](./categories#software-development) - Best practices and architecture
- [Technical Interviews](./categories#technical-interviews) - Career guidance and interview prep
- [Book Reviews](./categories#book-reviews) - Technical and programming book reviews
- [Personal Experiences](./categories#personal-experiences) - Journey and learning experiences

## About the Author

[Bazlur Rahman](./about) is a Java Champion and experienced Software Engineer with over a decade of professional experience. He is an author, speaker, and active contributor to the Java community.

## Recent Activity

- Writing "[Modern Concurrency in Java](https://learning.oreilly.com/library/view/modern-concurrency-in/9781098165406/)" for O'Reilly
- Editor at [InfoQ](https://www.infoq.com/profile/A-N-M-Bazlur-Rahman/)
- Contributing at [Foojay.io](https://foojay.io/today/author/bazlur-rahman/)

[View All Posts](./categories) | [About](./about)
