Guitar Hero Shopping Extravaganza
---

## Business Requirements

- Guest user
  - register as user (with unique username/password)
  - login w/ credentials
  - see catalog of guitars
- Authenticated user
  - add products to cart
  - view products in cart
  - modify count of products in cart
  - checkout
    - external payments API
    - persist orders in DB
  - list existing orders
  - log out
- Admin
  - add brands
  - add categories
  - add products to the catalog
  - modify the prices of products

## Third-Party Payments API

POST ...

```json
{
  "user_id": "abc123...",
  "total": 342.42,
  "card": {
    "name": "Bart Simpson",
    "number": 012345678910,
    "expiration": "0821",
    "ccv": 123
  }
}
```

On Success

```json
{
  "payment_id": "..."
}
```

Response Codes:

- 200: Success
- 400: Invalid Request Body
- 409: Conflict, duplicate payment
- 500: Unknown server error
