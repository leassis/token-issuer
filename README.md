# JWT token issuer

This project is part of my kotlin learnings and it's intend to create jwt tokens to use internally between services, although the `keys` endpoint is open, I would use this service only in a private networkd


# Development  
This is a kotlin project, meaning you MUST need `java virtual machine` I've tested in JAVA 11 but it should work with 8+.

The passwords in the files are bcrypted.

The private key is in pem format.

You can configure several users each will have their on issuer and credentials. The drawback is that everyone will use the same key pair

The data inside the JWT token is only the basic and mandatory fields, you can extend adding more properties to JwtInfo class

The default credentials it default:default you can change it by changing the application.yml file 

# Docker
A docker file is shipped with the code, you just need to build it and start using. Send 2 build args to create the image

- PEM_PATH  where the pem file is located
- APP_PATH  where the fat jar is located
- CONFIG_PATH where the application.yml for the external configuration is located, there is a file called application-demo-yml, you can use this file as base

` docker build --build-arg PEM_PATH=./issuer.pem  --build-arg APP_PATH=.//build/libs/token-issuer-0.0.1-SNAPSHOT.jar --build-arg CONFIG_PATH=./src/main/resources/application-demo.yml -t token-issuer .`
