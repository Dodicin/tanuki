## [Processo e Sviluppo del Software] - Assignment 3

## Author
- **Nassim Habbash** - _808292_

## Getting Started

```
$ git clone https://gitlab.com/Dodicin/tanuki
$ cd tanuki
```

# Tanuki

<center>
<img src="docs/images/logo.jpg" width="100" />
</center>

Tanuki is a social platform based on content-sharing. Amidst the variety of social network existing nowadays, Tanuki tries to take a mixed stance between what the ecosystem offers today: it allows for the production and sharing of content (*i.e. videos, images, audio, etc*), and also communication through posts displayed on a timeline.

Each user has its own channel, where they can push content. Content can be of the kinds described above. Users have also the possibility to post. Users can follow other users, and their timeline is made up of the posts of the users they're following. Posts can be either *generic*, and appear in the timeline as normal posts or answers to other posts, or can belong to a board. 
Boards are essentially categories (*i.e. music, literature, etc*). Posts on boards are organized in threads.


<center>
<img src="docs/images/mock.png"  />
</center>

*Ugly mockup of the homepage*

# Overview

The project itself aims to implement CRUD operations on the main entities that form the system: *Users, Channels, Content, Posts* and *Threads*.

# Architecture

For the development of the system it has been chosen to use the following tools: Spring framework, JPA through Hibernate for persistency, MySQL as the DBMS and Maven for dependency management.

# ER diagram

<center>
<img src="docs/images/er.png"  />
</center>

