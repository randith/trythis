# somethingelse


Given time constraints and the breadth of the problem, I am going to treat this a little more like a design exercise rather than a coding exercise.  Real world, faced with this problem I would not jump to coding but focus on how to design an MVP that can through iterations pivot to the changing requirements.  I hope this properly provides insight into my thinking and way of working.  I am more than happy to do more coding in the future if that helps.


## Design steps
First, reading through the use cases this problem seems to fall nicely under the feed/inbox set of problems.  Which is good, as that is a well understood space.  Skipping the, "just use something that already exists" solutions I focused on 3 things:
1.	Build some understanding on how the data will be used
2.	Design the APIs and data structures 
3.	Pick initial DB and schema to back the APIs

#### Build some understanding on how the data will be used

I spent a bit of time looking into what are the expectations of web viewers of markdown.  Looking at a few different libraries such as [showdownjs](https://github.com/showdownjs/showdown) it looks like a string representation of markdown will work.  I suspect that more research should be done to verify more complex character encoding (if i18n is a concern) and more complex content such as embedded images.  But I am considering that out of scope for now.

#### Design the APIs and data structures

Next, I set out to design the api in swagger.  My goal is to set the initial public (at least from the API perspective) signatures and data structures used to accomplish the business use cases.

The first go definitions are found at [swagger.yaml](https://github.com/randith/somethingelse/blob/master/swagger.yaml) which if you copy into the [online swagger editor](https://editor.swagger.io/) gives a reasonable view of the APIs and the structures behind them.

If I was taking this forward for real I would spend much more time on these definitions and make the "try this" calls functional.  I have had a lot of success using swagger as the documented contract between api dev, ui dev, and PM/POs that are more on the technical side.

#### Pick initial DB and schema to back the APIs

Given the problem and scale desired I would go with Mongo or PostGres (primarily using its document store capability).

The primary use case described is a feed/inbox and document stores work really well for that problem space.  That is that we are storing and querying documents that fit nicely into json or json like structures.  We also do not have a strong transactional requirement outside of auth.  So, we can get much more scale by choosing a database the relaxes on consistency.

Another part of the problem that might be a bit different is the timeseries based queries required to determine who was in what group at a time in the past.  However, I am not worrying about it much as I am going with a simplifying assumptions that messages are sent now, meaning our current requirement is to keep track of who is in a group now, not at some arbitrary time in the past.

Looking forward another interesting possibility would be to use ElasticSearch and store the content (markdown blogs themselves) in a different store.  This would allow the metadata DB that poweries queries to be much smaller and much more eficient.  Also, it allows the api to offload most of the network throughput to something like S3.
 
 
## Java Api

In order to have some code for this coding project I quickly put together a starter api that has 3 endpoints (create post, get posts, and get post) back by an in memory PostService.

The java api is a dropwizard service that has some tests, reasonable build and test cycle powered by maven, and an integration test powered by RestAssured.

I ran it in my IDE.  But it can be run via:
* ```cd PATH/TO/REPO/inboxapi```
* ```mvn package```
* ```java -jar target/inboxapi.jar server app/config.yaml```

then you can do things like

```
curl -X POST "http://localhost:8080/v1/posts" -H "accept: application/json" -H "Authorization: 12456" -H "Content-Type: application/json" -d "{ \"id\": \"AAA51bf03e0-1d77-11e8-b467-0ed5f89f718b\", \"content\": \"My awesome post\", \"authorId\": \"66f99a4a-1d77-11e8-b467-0ed5f89f718b\", \"destinationType\": \"user\", \"destinationId\": \"987864ca-1d77-11e8-b467-0ed5f89f718b\", \"timestamp\": 1519925857}"  | python -m json.tool
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   371  100   130  100   241  16722  31000 --:--:-- --:--:-- --:--:-- 34428
{
    "id": "AAA51bf03e0-1d77-11e8-b467-0ed5f89f718b",
    "link": "/v1/posts/AAA51bf03e0-1d77-11e8-b467-0ed5f89f718b",
    "timestamp": 1519925857
}
```

and 

```
curl localhost:8080/v1/posts | python -m json.tool
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   330  100   330    0     0  41777      0 --:--:-- --:--:-- --:--:-- 47142
[
    {
        "id": "51bf03e0-1d77-11e8-b467-0ed5f89f718b",
        "link": "/v1/posts/51bf03e0-1d77-11e8-b467-0ed5f89f718b",
        "timestamp": 1519925857
    },
    {
        "id": "anotherone",
        "link": "/v1/posts/anotherone",
        "timestamp": 1519925857
    },
    {
        "id": "AAA51bf03e0-1d77-11e8-b467-0ed5f89f718b",
        "link": "/v1/posts/AAA51bf03e0-1d77-11e8-b467-0ed5f89f718b",
        "timestamp": 1519925857
    }
]
```

and

```
curl localhost:8080/v1/posts/anotherone | python -m json.tool
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   200  100   200    0     0  13825      0 --:--:-- --:--:-- --:--:-- 14285
{
    "authorId": "66f99a4a-1d77-11e8-b467-0ed5f89f718b",
    "content": "My awesome post",
    "destinationId": "987864ca-1d77-11e8-b467-0ed5f89f718b",
    "destinationType": "user",
    "id": "anotherone",
    "timestamp": 1519925857
}
```
