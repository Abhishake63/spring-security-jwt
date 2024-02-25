# Spring Boot JWT Project

Welcome to our guide on implementing token-based authentication with Spring Boot and Spring Security using JSON Web Tokens (JWT). In this tutorial, we'll walk you through the process of setting up a secure authentication system for your Spring Boot application, allowing users to register, log in, and access protected resources using JWT-based authentication.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project serves as a starting point for understanding how to create a basic Spring Boot application that uses Spring Security as it's security protocol. It includes the necessary setup and dependencies to quickly get you up and running.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed (version 17 or higher)
- Maven build tool installed
- PostgreSQL installed

## Getting Started

To get a local copy of the project up and running, follow these steps:

1. Clone the repository

   ```sh
   git clone https://github.com/Abhishake63/spring-security-jwt.git
   ```

2. Change directory

   ```sh
   cd spring-security-jwt
   ```

3. Create a user named 'dynamic' with password 'dynamic'

   ```sh
   sudo -u postgres createuser --pwprompt dynamic
   ```

4. Create a database named 'springsecurity' and give permissions to the user 'dynamic'

   ```sh
   sudo -u postgres createdb -O dynamic springsecurity
   sudo -u postgres psql -c 'GRANT ALL ON DATABASE springsecurity TO dynamic;'
   ```

5. Build the project and Run the application

   ```sh
   mvn clean spring-boot:run
   ```

## Usage

### Admin Registration

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"123456"}' http://localhost:8081/auth/admin/reg -w "\n"
```

### Admin Login

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"123456"}' http://localhost:8081/auth/admin/login -w "\n"
```

### User Registration

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username" : "user","password":"123456"}' http://localhost:8081/auth/user/reg -w "\n"
```

### User Login

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username" : "user","password":"123456"}' http://localhost:8081/auth/user/login -w "\n"
```

### Public Admin Api

After Login with Admin Creds, you will get a bearer token use that token below to access that API.

```sh
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8081/api/public/admin/ -w "\n"
```

### Public User Api

After Login with User Creds, you will get a bearer token use that token below to access that API.

```sh
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8081/api/public/user/ -w "\n"
```

Feel free to explore the code to understand how the Spring Boot application is structured.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, follow these steps:

1. Fork the project from the GitHub repository.
2. Create a new branch with a descriptive name.
3. Make your desired changes.
4. Commit and push your changes to your fork.
5. Create a pull request detailing your changes.

## License

This project is licensed under the [MIT License](LICENSE).

---

Happy coding!

For more information about Spring Boot, visit the [official documentation](https://spring.io/projects/spring-boot).
