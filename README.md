# Spring Boot JWT Project

Welcome to the Spring Boot "JWT" project! This is a simple project that demonstrates the features of Spring Security.

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
curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"123456"}' http://localhost:8080/api/v1/adminRegister -w "\n"
```

### Admin Login

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username":"admin","password":"123456"}' http://localhost:8080/api/v1/adminLogin -w "\n"
```

### Teacher Registration

After Login with Admin Creds, you will get a bearer token use that token below to register a teacher.

```sh
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN>" -d '{"username":"teacher","email":"teacher@gmail.com","password":"123456"}' http://localhost:8080/api/v1/admin/register -w "\n"
```

### Teacher Login

```sh
curl -X POST -H "Content-Type: application/json" -d '{"email":"teacher@gmail.com","password":"123456"}' http://localhost:8080/api/v1/teacherLogin -w "\n"
```

### Public Api

After Login with Teacher Creds, you will get a bearer token use that token below to access that API.

```sh
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/public/ -w "\n"
```

### Student Registration

```sh
curl -X POST -H "Content-Type: application/json" -d '{"username" : "student", "email":"student@gmail.com","password":"123456"}' http://localhost:8080/api/v1/studentRegister -w "\n"
```

### Student Login

```sh
curl -X POST -H "Content-Type: application/json" -d '{"email":"student@gmail.com","password":"123456"}' http://localhost:8080/api/v1/studentLogin -w "\n"
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
