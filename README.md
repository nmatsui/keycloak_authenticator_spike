# keycloak\_authenticator\_spike

This is a spike repository for examining keycloak authenticator. 

* [keycloak/secretword-checkapi-caller-authenticator](./keycloak/secretword-checkapi-caller-authenticator)
  * This is a plugin for keycloak that works as an Authentication SPI which extends keycloak's [UsernamePasswordForm](https://github.com/keycloak/keycloak/blob/main/services/src/main/java/org/keycloak/authentication/authenticators/browser/UsernamePasswordForm.java). It asking for a secret word in addition to username and password, and sends the secret word to the [secretword\_checker](./secretword_checker) api. If an error is returned from the api, the user's authentication fails.
* [relying\_party](./relying_party)
  * This is a sample Relying Party that requests an authentication to keycloak by using OpenID Connect.
* [secretword\_checker](./secretword_checker)
  * This provides a REST API to validate the secret words.

## How to use
1. `docker-compose build`
2. `docker-compose up`
3. access [index page of sample relying party](http://localhost:3000)
4. log in to sample site by using below parameters:
  * username: testuser
  * password: passwd
  * secretword: checkapi

## License

[MIT License](/LICENSE)

## Copyright
Copyright (c) 2023 [Nobuyuki Matsui](mailto:nobuyuki.matsui@gmail.com)

