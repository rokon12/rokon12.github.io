---
layout: default
title: Categories
---

# Blog Categories

Browse articles by category. Each post may appear in multiple categories based on its content.

## Java Programming

{% assign posts = site.static_files | where_exp: "file", "file.path contains 'backup' and file.extname == '.md' and file.path != '/backup/index.md'" | sort: "modified_time" | reverse %}

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Java" or post_content contains "java" or post_content contains "JVM" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

## Thread Programming

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Thread" or post_content contains "Concurrent" or post_content contains "Parallel" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

## Software Development

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Software" or post_content contains "Development" or post_content contains "Programming" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

## Technical Interviews

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Interview" or post_content contains "Career" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

## Book Reviews

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Book" or post_content contains "Review" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

## Personal Experiences

{% for file in posts %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% assign lines = post_content | newline_to_br | split: '<br />' %}
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

{% if post_content contains "Experience" or post_content contains "Journey" or post_content contains "Story" %}
### [{{ title }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
*{{ date | date: "%B %d, %Y" }}*
{% endif %}
{% endfor %}

[Back to Home](./)