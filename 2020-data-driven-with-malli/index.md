
# Data-Driven Rapid Application Development with Malli

----


# Data-Driven Rapid Application Development with Malli
{: style="font-size: 4rem; text-align: center;"}

----

# 1. Malli Basics
{: style="font-size: 3rem; text-align: center; margin-top: 3em;"}

----

# 2. Single Source of Truth
{: style="font-size: 3rem; text-align: center; margin-top: 3em;"}

----

# Domain Entities

Lots of duplicated information

- Database schema
- API definitions
- Form definitions
- Validation
- Generation

----

# Schemas everywhere

- Datomic
- Datascript
- Lacinia
- Specmonstah
- Malli
- Spec

----

# Single Source of Truth?

- Great in theory
- Move fast in the beginning
- But mapping is not always 1:1
- Need ways to override based on context
- Let's try it anyway, with Malli!

----

```
➜ tree data/schema
data/schema
├── entities.edn
├── attributes.edn
└── enums.edn
```

----

``` clojure
;; entities.edn

{:acme/address
 [:map {:datomic? true}
  [:address/company {:ui/label {:de "Firma"} :optional true} string?]
  [:address/street {:ui/label {:de "Strasse"}} string?]
  [:address/country {:ui/label {:de "Land"}} string?]
  [:address/zip {:ui/label {:de "PLZ"}} string?]
  [:address/place {:ui/label {:de "Ort"}} string?]]

  ,,,}
```

Single source of truth for all domain entities.

----

```clojure
;; entities.edn
{,,,

 :acme/customer
 [:map {:datomic? true}
  [:customer/address {:ui/label {:de "Name"}} [:acme/ref :acme/address]]
  [:customer/email {:ui/label {:de "Email"}}
   [:re {:error/message {:en "Invalid email address"
                         :de "Email-Adresse ungültig"}}
    #"^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"]]]

 ,,}
```

----

```clojure
;; entities.edn
{,,,

 :acme/customer
 [:map {:datomic? true}
  [:customer/address {:ui/label {:de "Name"}} :acme/address]
  [:customer/email {:ui/label {:de "Email"}}
   [:re {:error/message {:en "Invalid email address"
                         :de "Email-Adresse ungültig"}}
    #"^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"]]]

 ,,}
```

----

``` clojure
;; enums.edn

{:acme/role
 {:role/customer   {:ui/label {:en "Customer" :de "Kunde"}}
  :role/sales      {:ui/label {:en "Sales" :de "Vertrieb"}}
  :role/costing    {:ui/label {:en "Costing" :de "Kalkulation"}}}

  ,,,}
```

Enum values, turn into `:db/ident` in Datomic, keywords in DataScript

``` clojure
  [:customer/role {:ui/label "Role"} [:acme/enum :acme/role]]
```

----

```
;; attributes.edn

[{:db/ident       :acme/uuid
  :db/valueType   :db.type/uuid
  :db/cardinality :db.cardinality/one
  :db/unique      :db.unique/identity}

 {:db/ident       :acme/updated-at
  :db/valueType   :db.type/instant
  :db/cardinality :db.cardinality/one}

  ,,,]
```

Meta attributes that are added to every entity.

----

``` clojure
(defn datomic-schema []
  ,,,)

(defn datascript-schema []
  ,,,)
```

Fairly trivial to generate these, but already quite useful.

---

``` clojure
(for [[schema]        (schema/entity-schemas opts)
         :when           (:datomic? (m/properties schema opts))
         [ident props t] (mu/map-entries schema opts)]
     (into {:db/ident           ident
            :db/valueType   (value-type (m/form t opts))
            :db/cardinality :db.cardinality/one}
           (filter (comp #{"db"} namespace key))
           props))
```

Malli schemas are a great input format, plenty of ways to walk, query,
transform.

----

``` clojure
(defn specmonstah-schema []
  ,,,)

(datomic/transact
  (specmonstah/generate {:acme/customer [[2]
                                         [1 {:refs {:customer/address :address-1}}]]
                         :acme/address [[5]]}))
```

Specmonstah needs a "schema" to know about relationships, with this we basically get test factories for free.

----

``` clojure
(ns acme.schema
  (:require [malli.core :as m]))

(def registry
  "Map of schemas, all the Malli defaults,
  plus the ones for our own enums and
  entities."
  (merge m/default-registry
         (enum-schemas)
         (entity-schemas)
         {:ars/ref ref-schema
          :ars/enum enum-schema}))
```

Now we just want to pass this registry in every time we call Malli (and some other defaults like `:locale` and `:errors`).

----

``` clojure
;; transaction data
[{:db/id 123
  :customer/address 456}

 {:db/id 456
  ,,,}]

;; d/entity API
[{:db/id 123
  :customer/address {:db/id 456 ,,,}}]

;; temp-ids, lookup refs
[{:db/id [:user/name "plexus"]
  :customer/address "addr-1"}
 {:db/id "addr-1" ,,,}]
```

We can change the meaning of `:ars/ref` on the fly

----

``` clojure
(if (= :ars/ref (m/name schema))
  (let [ref (first (m/children schema))]
    ,,,))


```

We retain information about the relationship, but we can also just treat it as a
`MapSchema`.

----

``` clojure
(def validate (wrap2 m/validate))
(def explain (wrap2 m/explain))
(def humanize (wrap1 me/humanize))
(def into-schema (wrap3 m/into-schema))
(def schema (wrap1 m/schema))
(def name (wrap1 m/name))
(def form (wrap1 m/form))
(def children (wrap1 m/children))
(def properties (wrap1 m/properties))
(def generate (wrap1 mg/generate))
(def map-entries (wrap1 m/map-entries))
(def accept (wrap2 m/accept))
,,, ;; about a dozen more
```

Ended up writing lots of wrappers to inject these defaults...

----

With this wrapper we can now do things like

``` clojure
(schema/validate :acme/customer {...})
```

----

# 3. Forms
{: style="font-size: 3rem; text-align: center; margin-top: 3em;"}

---

``` clojure
[f/form {:schema :acme/customer
         :init {:customer/address {:address/city "Berlin"}}
         :on-submit (fn [customer] ,,,)}
 [f/scaffold]
 [f/submit]]
```

Formik style form helpers, knows which fields there are because, their labels,
if they are required, can validate the result and show validation errors.

----

``` clojure
[f/form {:schema :acme/customer
         :init {:customer/address {:address/city "Berlin"}}
         :on-submit (fn [customer] ,,,)}

 [:section
  [f/input-for :customer/email]
  [:h2 "Address"]
  [f/input-for [:customer/address :address/street]]
  [f/input-for [:customer/address :address/city]
   {:label "Municipality"
    :component f/text-area}]]

 [f/submit {:label "Send!"}]]
```

Still possible to do arbitrary styling and markup, provide custom components, change labels, etc.

----

``` clojure
[f/form {:schema (schema/deep-optional-keys :acme/customer)}
  ,,,]

[f/form {:schema (schema/select-keys :acme/customer [:customer/email ...])}
  ,,,]


[f/form {:schema [:map [:foo string?]]}
  ,,,]
```

Here flexibility of Malli really payed off.

----

## Conclusion

- Considered a success
- Add an entity/attribute and everything "just works", from frontend to database
- Everyone on the team could confidently edit the schema
- Flexibility is essential, Malli made it easy to adjust to context

----
{:.header}

# THE END
