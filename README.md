# REST API and SPA Example Project

> This repository contains an example of a web application based on a RESTful API with Spring Boot (`api/`) and a single page application with Vue.js v3 (`frontend/`).
> Please refer to the `README.md` files in the respective folders for concrete instructions.

## Quick Start

1. Clone this repository.
2. Follow the [api/README.md](/api/README.md) instructions in the `/api` folder to install the prerequisites for the backend.
3. Follow the [frontend/README.md](/frontend/README.md) instructions in the `/frontend` folder to install the prerequisites the frontend.
4. Start the API by navigating into its folder and executing 
    - `./mvnw spring-boot:run`
5. Now start the frontend by navigating into its folder and executing
    - `npm install`
    - `npm run dev`

## Usage

Assuming all prerequisites are fulfilled, you can follow these instructions to get to know the application:

### Testing the backend
- Open http://localhost:8080/api/v1/todos in your browser. It will show all available todos in the system.
- Open http://localhost:8080/api/v1/todos/1 in your browser. It will show the todo with ID `1`.
- Open http://localhost:8080/api/v1/todos/42 in your browser. It will show a `404 Not found` error because there is no todo with ID `42` in the system.

### Testing the frontend
- It will host the UI at http://localhost:5173, which you can now also open in your browser.
- Navigate to the cats view by clicking on the respective menu item or via the direct ULR http://localhost:5173/#/todos.
- Delete a cat. Then confirm its deletion via the API by opening http://localhost:8080/api/v1/todos in your browser again.
