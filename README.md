# REST API and SPA Example Project

> This repository contains an example of a web application based on a RESTful API with Spring Boot (`api/`) and a single page application with Vue.js v2 (`frontend/`).
> Please refer to the `README.md` files in the respective folders for concrete instructions.

## Quickstart

Assuming all prerequisites are fulfilled, you can follow these instructions to get to know the application:
- Start the API by navigating into its folder and executing `./mvnw spring-boot:run`.
- Open http://localhost:8080/api/v1/todos in your browser. It will show all available todos in the system.
- Open http://localhost:8080/api/v1/todos/1 in your browser. It will show the todo with ID `1`.
- Open http://localhost:8080/api/v1/todos in your browser. It will show all available assignees in the system.
- Open http://localhost:8080/api/v1/todos/1 in your browser. It will show the assignee with ID `1`.
