# Lunera Score Bot for Bootcamp 4

A [Spring Boot](http://projects.spring.io/spring-boot/) API which notify people when Bootcamp 4 scores have been updated.

## Requirements

For building and running the application you need:

- [JDK 11](https://adoptium.net/temurin/releases/?version=11)
- [Maven 3](https://maven.apache.org/download.cgi)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.lunera.bot.LuneraBotApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Deploying the application to OpenShift

The easiest way to deploy the sample application to OpenShift is to use
the [OpenShift CLI](https://docs.openshift.org/latest/cli_reference/index.html):

## Copyright

Released under the Apache License 2.0. See
the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
