# A hypermedia Success Story

---

## Hypermedia

An API that works like the web

---

```
$ curl https://bacontheatre.ticketsolve.com/api/
{
  "id": 212,
  "name": "Bacon Theatre",
  "currency": "GBP",
  "_links": {
    "profile": [ { "href": "http://api.ticketsolve.com/profile/account" } ],
    "self": { "href": "/api/accounts/212" },
    "http://api.ticketsolve.com/rel/shows": { "href": "/api/shows" },
    "http://api.ticketsolve.com/rel/show": {
      "href": "/api/shows/{id}",
      "templated": true
    },
    "http://api.ticketsolve.com/rel/cart": { "href": "/api/cart" },
    "http://api.ticketsolve.com/rel/home": { "href": "http://www.bacontheatre.co.uk/" }
  }
}
```
