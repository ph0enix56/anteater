# anteater
Backend of D&amp;D app for bachelor's project.

Currently in early experinmental development stage, uses Spring Boot with MongoDB.

## Setup
Set your MongoDB connection string as an environment variable named `MONGODB_URI` or change the value in `application.properties` file.
Start the app with gradle wrapper `./gradlew bootRun` or build a jar with `./gradlew build` and run it with `java -jar build/libs/anteater-0.0.1-SNAPSHOT.jar`.
