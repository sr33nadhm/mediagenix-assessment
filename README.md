# Mediagenix Assessment Project

This project is a Java Spring Boot based backend application which exposes REST API endpoints to manage books and collections of books.

## Prerequisites

- Java 17: [Install Java 17](https://adoptium.net/?variant=openjdk17)
- Maven: [Install Maven](https://maven.apache.org/install.html)
- PostgreSQL: [Install PostgreSQL](https://www.postgresql.org/download/)
- A database named `books_db` created in PostgreSQL

Alternatively, the application can be run using [Docker](#getting-started--for-docker), if the above requirements are not met.

### Getting Started : For Local Run

1. Navigate to the project directory:

   ```shell
   cd assessment
   ```

2. Install Dependencies:

   Use Maven to install the project dependencies by running the following command:

   ```shell
   mvn clean install
   ```

   This will download the required dependencies and build the project.

3. Run Tests:

   Use Maven to run the testcases for the application, by running the following command:

   ```shell
   mvn clean test
   ```

   This will run all the test cases specified, for the application. There will be H2 in-memory database setup, and used for the testing purpose

4. Start the application:

   Once the dependencies are installed, you can start the application by running the following command:

   ```shell
   java -jar target/assessment-0.0.1.jar
   ```

   This command runs the application using the generated JAR file located in the `target` directory.

5. Access the application:

   Once the application is running, you can access the APIs in the application by importing [postman](https://www.postman.com/downloads/) [collection](MediaGenix_Test.postman_collection.json) associated.

6. Stopping the Application

   To stop the application, you can press `Ctrl + C` in the terminal where it is running. This will terminate the application.

### Getting Started : For Docker

1. Make sure you have the following software installed on your machine:

   - Docker: [Install Docker](https://docs.docker.com/get-docker/)
   - Postman: [Install Postman](https://www.postman.com/downloads/)

2. Extract the zip file

3. Navigate to the project directory:

   ```shell
   cd assessment
   ```

4. Customize Configuration (optional):

   If needed, you can modify the environment variables and configuration in the `docker-compose.yml` file to match your specific requirements.

5. Start the application:

   Run the following command to start the application using Docker Compose:

   ```shell
   docker-compose up
   ```

   This command will build the images `assessment` and `postgress`, create and start the containers `mediagenix-test` and `postgress-db` as defined in the `docker-compose.yml` file.

6. Access the application:

   Once the containers are up and running, you can access the APIs in the application by importing postman [collection](MediaGenix_Test.postman_collection.json) associated.

7. Stopping the Application

   To stop the application and shut down the containers, you can use the following command:

   ```shell
   docker-compose down
   ```

   This command will stop and remove the containers, while preserving any data stored in volumes.
