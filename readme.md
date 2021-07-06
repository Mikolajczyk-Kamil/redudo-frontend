Redudo 
===

#### Frontend of `Redudo` book manager. There is [backend](https://github.com/Mikolajczyk-Kamil/redudo-backend "Backend").

Preview
---
[redudo.heroku.com](https://redudo.herokuapp.com "Redudo")

Server's wake up can take about minute

Table of Contents
---
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

General info
---
The Redudo is made for managing your books. After sign in via Google you can search for them in the database
(if there is not the book in the database, backend app use [Google Books Api](https://developers.google.com/books "Google Books Api")).\
Then you can add the book to one of three lists:
* books to read (to **RE**ad)
* books already reading (**DU**ring)
* done books (**DO**ne)

Technologies
---
Project is created with:
* Vaadin (version 14)
* Java (version 11)
* CSS
* JavaScripts

Setup
---
To run project you have to set environment variables necessary for OAuth2 authorization:
* CLIENT_ID
* CLIENT_SECRET

To get it you have to create app in [Google Developer Console](https://console.cloud.google.com/apis/credentials "Google Developer Console")
