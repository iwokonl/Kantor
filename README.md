# Currency Exchange Service
This Spring Boot application provides a robust solution for fetching and storing currency exchange rates from the National Bank of Poland (NBP) API. Designed with modern software development practices in mind, it utilizes a Model-View-Controller (MVC) architecture to ensure clean separation of concerns and enhance maintainability.

## Description

Provide a more detailed explanation of your project here. Highlight the problem your application aims to solve, its key features, and its overall architecture. Mention the technologies used, such as Angular TypeScript for the frontend, Spring Boot for the backend with a microservices approach, and the integration of Kafka for message brokering and PostgreSQL for data persistence.

## Table of Contents

- [Installation](#installation)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installing](#installing)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [Versioning](#versioning)
- [Authors](#authors)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Installation

This section provides a step-by-step guide on how to get a development environment running.

### Prerequisites

Before installing, ensure you have the following software installed on your machine:

- Node.js 20.10.0
- JDK 17
- Apache Kafka 3.6.1
- PostgreSQL 16.2
- Spring boot 3.2.3
- Angular 17.2.1
- Java 20.0.2

### Installing

Follow these steps to set up your development environment:

```bash
# Clone the project repository
git clone https://yourproject.github.io
cd yourproject

# Set up the Angular frontend
cd frontend
npm install

# Set up the Spring Boot backend
cd ../backend
./mvnw install
