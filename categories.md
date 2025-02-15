---
layout: default
title: Categories
---

# Blog Categories

Browse articles by category. Each post may appear in multiple categories based on its content.

## Java Programming

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Java' or post_content contains 'java' or post_content contains 'JVM' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

## Thread Programming

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Thread' or post_content contains 'Concurrent' or post_content contains 'Parallel' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

## Software Development

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Software' or post_content contains 'Development' or post_content contains 'Programming' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

## Technical Interviews

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Interview' or post_content contains 'Career' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

## Book Reviews

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Book' or post_content contains 'Review' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

## Personal Experiences

{% for file in site.static_files %}
{% if file.path contains 'backup/' and file.extname == '.md' and file.name != 'index.md' %}
{% capture post_content %}{% include_relative {{ file.path }} %}{% endcapture %}
{% if post_content contains 'Experience' or post_content contains 'Journey' or post_content contains 'Story' %}
### [{{ file.basename | replace: '-', ' ' | capitalize }}]({{ site.baseurl }}{{ file.path | remove: '.md' }})
{% endif %}
{% endif %}
{% endfor %}

[Back to Home](./)
