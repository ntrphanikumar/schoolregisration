### Admin Token

* POST https://localhost:8080/oauth/token?grant_type=password
* RequestHeaders
  * Authorization: Basic Auth with $clientid:$clientsecret
  * Content-Type: application/x-www-form-urlencoded
* RequestBody
  * username: $adminusername
  * password: $adminpassword
* ResponseBody
  ```
  {
    "access_token": "OrdrZMjLT_EotJP1G7sn2CAjMGI",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read write user_info"
  }
  ```