# Cinema-Details
This project was developed using Spring Boot 3 and Spring Security 6, incorporating JWT-based authentication for secure access. It also included the use of method-level security to ensure fine-grained control over API access. The implementation featured a custom user details service for user management and handling authentication, along with custom exception handling to provide meaningful error responses and improve the application's robustness.

The system was designed to support two primary roles: Admin and User.

Admins were granted permissions to perform actions such as creating, updating, and deleting resources via the respective APIs.
Users and non-registered users were restricted to accessing the GET APIs only, ensuring that non-privileged users couldn't perform any modifications to the data.
The application adhered to a three-layered architecture for better separation of concerns, modularity, and maintainability:

Controller Layer: Handled incoming HTTP requests and responses, serving as the interface between the client and the application.
Service Layer: Contained the core business logic of the application, ensuring clean and reusable code.
Repository Layer: Managed database interactions using Spring Data JPA, enabling seamless communication with the underlying MySQL database.
This structured approach, combined with the use of modern security practices and a well-defined role-based access control system, ensured that the application was scalable, secure, and easy to maintain.

//Ajay.K
