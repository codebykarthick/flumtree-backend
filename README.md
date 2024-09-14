# Flumtree Backend

## Introduction
This is an open source Spring Boot REST backend application loosely based on a
certain market for buying used items.

## Tech Stack
This uses Spring Boot 3.3.3 and was tested under Java JDK 17. Has startup data
seed which creates sample data for usage into H2 database, during application
startup. (All data will be destroyed at the end of application cycle).

## Usage
### Startup
To run locally, run in the root or cd into deploy folder and adapt the commands
accordingly.

```bash
sh deploy/run.sh # Builds the jar and runs directly in your terminal
sh dep[loy/build.sh # To build the JAR only available under the build folder.
```

I have also written a Dockerfile if you want to build and deploy it as a docker
container for lesser maintanence.

### Swagger
Once application is up and running, the swagger document can be accessed over at
[/swagger-ui/index.html]("https://localhost:8080/swagger-ui/index.html"), which is hosted with 
Spring Fox.

### H2 Database
This project uses H2 temporary database with certain seed data for usage, any
other data that is created or modified will persist only till the lifetime of
the application, so beware. The H2 Console can be accessed at 
[/h2-console]("https://localhost:8080/h2-console")

## Testing
Testing of endpoints while the application is running can be done 
in multiple ways. Apart from consuming it from your installed app, 
REST clients like Postman, Insomnia, etc, the below can be used as well.

### Swagger
Included swagger can be opened in browser at the above-mentioned path and each
endpoint can be validated.

### VSCode Rest Client
If you have the "Rest Client" extension in VSCode, I have included the test
.http files that can be executed using this extension, which can be found
under the **"rest"** folder.