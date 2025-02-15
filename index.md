---
layout: default
title: Home
---

# Welcome to Bazlur's Blog Archive

A comprehensive collection of articles on Java, Software Development, and Technology. This archive contains technical articles, tutorials, and insights from my years of experience in software development.

## Recent Posts

{% assign posts = site.static_files | where_exp: "file", "file.path contains 'backup' and file.extname == '.md' and file.path != '/backup/index.md'" | sort: "modified_time" | reverse %}

{% for file in posts limit:10 %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
{% assign front_matter = false %}
{% assign title = "" %}
{% assign date = "" %}

{% for line in lines %}
  {% if line contains "title: " %}
    {% assign title = line | replace: "title: ", "" | strip %}
  {% endif %}
  {% if line contains "date_scraped: " %}
    {% assign date = line | replace: "date_scraped: ", "" | strip %}
  {% endif %}
{% endfor %}

### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*

{% endfor %}

## Categories

- [Java Programming](./categories#java-programming)
- [Thread Programming](./categories#thread-programming)
- [Software Development](./categories#software-development)
- [Technical Interviews](./categories#technical-interviews)
- [Book Reviews](./categories#book-reviews)
- [Personal Experiences](./categories#personal-experiences)

## About the Author

[Bazlur Rahman](./about) is a Java Champion and experienced Software Engineer with over a decade of professional experience. He is an author, speaker, and active contributor to the Java community.

## Recent Activity

- Writing "[Modern Concurrency in Java](https://learning.oreilly.com/library/view/modern-concurrency-in/9781098165406/)" for O'Reilly
- Editor at [InfoQ](https://www.infoq.com/profile/A-N-M-Bazlur-Rahman/)
- Contributing at [Foojay.io](https://foojay.io/today/author/bazlur-rahman/)

[View All Posts](./categories) | [About](./about)