
# Feature Flags

Lightning talk for the Ruby User Group Berlin, 2 July 2015

Use the space bar or arrow keys to navigate

----
{: fullscreen-img="img/ts.svg"}


----
{: fullscreen-img="img/ts_site.png"}



---

```
Features.config do
  # The new Ember frontend
  feature :ticketbooth

  # Login in new front end, instead of using legacy pages
  feature :ticketbooth_login, :on

  # Use the new version of Google Analytics
  feature :universal_analytics

  # new absolute positioned layout
  feature :new_seat_layout

  # Ticketbooth optional checkout steps
  feature :checkout_product_prompt
  feature :checkout_donations_prompt
end
```

---

```
if Feature.on? :ticketbooth

else

end




<html>
  <body class="ticketbooth universal_analytics">
    ...
```

---

## Layered system

```
# Global on/off
Feature.enable! :new_seat_layout

# Per account
Account.find(42).enable_feature! :new_seat_layout

# Per browser session
&enable_features=new_seat_layout
```

---

## Flags in Session

```
http://foo.ticketsolve.com/?enable_features=new_seat_layout

- enable_features
- disable_features
- reset_features

Great for testing
```

---

## Per spec group

```

context 'when features are on',
  enable_features: [:feat, :foot, :fast] do

  it 'will be awesome' do


  end

end
```



---

## Gradual rollout

```
- Give QA a link to test
- Enable for guinea pig customers
- Enable for all
```

---

## Sales person loves it

```
Easy to demo upcoming features

It looks cool
```

---

## Safety net

```
Wrap risky features in a feature flag.

When issues pop up: just disable,
no need to roll back deploy.
```

---
{: fullscreen-img="img/togl.png"}

---

## We're hiring :D

```
We're looking for two people, at least one
with good knowledge of front-end


Keywords: Ember, Rails, Hypermedia


- Contract position
- Work from anywhere
- Flexible hours
- Great people!
```
