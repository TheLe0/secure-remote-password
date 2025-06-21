# secure-remote-password

This is a simple CLI project of using the [SRP (secure remote password)](https://medium.com/cloud-security/secure-remote-password-spa-0f91a620ebca) protcol.

What this protocol contains:

- [X] Implementation of the SRP
- [X] Demonstration on how to use in a real application
- [X] Unit Tests

Next steps:

- [ ] More modes, maybe update password, forgot password and others
- [ ] Use a database to persist the data, instead of in-memory

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw -Dquarkus.args="<MODE> <USERNAME> <PASSWORD"
```

Where:

| Arg           | Description               | 
|---------------|---------------------------|
| MODE          | 'register' or 'login'     |
| USERNAME      |Your username              |
| PASSWORD      |Your password              |

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/secure-remote-password-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

