---
layout: default
title: Blog Archive
---

# Blog Archive

Welcome to the archive of blog posts from bazlur.ca. Here you'll find a collection of articles covering various topics including Java programming, software development, and technical insights.

## Posts

{% for file in site.static_files %}
  {% if file.path contains 'backup' and file.extname == '.md' and file.path != '/backup/index.md' %}
    * [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
  {% endif %}
{% endfor %}

## Categories

* Java Programming
* Thread Programming
* Software Development
* Technical Interviews
* Book Reviews
* Personal Experiences

All content is archived from the original blog at [bazlur.ca](https://bazlur.ca).