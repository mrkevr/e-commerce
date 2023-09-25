# E-Commerce
A simple monolithic e-commerce web application developed using Spring Framework, MySQL, and Bootstrap.

- [Introduction](#introduction)
- [Features](#features)
    - [Admin Features](#admin-features)
    - [Customer Features](#customer-features)
- [Dependencies](#dependencies)
- [Credits](#credits)
- [Screenshots](#screenshots)
    - [Database Schema](#database-schema) 
    - [Front Page](#front-page)
    - [Admin Interface](#admin-interface)
    - [Customer Interface](#customer-interface)

## Introduction
Welcome to E-Commerce Web Application repository! This project showcases a fully functional and feature-rich e-commerce platform built with Spring Framework, MySQL, and Bootstrap.

This E-Commerce Web Application is a robust and all-in-one solution designed to streamline the online shopping experience. It combines the power of Spring, a leading Java framework, with the reliability of MySQL for database management and the flexibility of Bootstrap for responsive front-end design.

## Features
### Admin Features
| Feature | Description |
| ------------- | ------------- |
| Admin Dasboard | A quick snapshot of your sales trends. Monitor customers, categories, products, orders with its detailed charts at a glance. |
| Customer Management | View customer info and their shopping cart. Monitor and update statuses of their orders. |
| Category Management | Ability to create and update categories. Admin can set the category's availability from the product catalog. |
| Product Management | Add, edit, or set activity of products from the catalog. Update product details, pricing, current stock and activity. Admin can also set product's discounted state and discounted price.|
| Order Management | Process orders, update statuses, and manage shipping details and date. |

### Customer Features
| Feature | Description |
| ------------- | ------------- |
| User Authentication and Account Management | Customers can choose their preferred authentication method. They can register using the registration form or log in with either Google or GitHub account. If it's the customer's first time logging in with Google or GitHub, an account will be created for them. Customers can view and manage their accounts, including updating personal information. |
| Product Catalog | Customers can browse all the available products from the catalog. Click on any product to view its details. |
| New Arrivals | New Arrival section showcases products that have just added to the catalog. |
| Discounted Items | Discounted Items section features products with special offers and reduced prices. |
| Shopping Cart | From the product page, customers can click "Add to Cart" button. The selected products will be instantly added to the customer's shopping cart. Customers can view and update their shopping cart. |
| Checkout | When the customer is ready to complete their purchase, they can simply click the "Checkout" button. Customers to provide shipping details and mode of payment upon checking out. |
| Order Monitoring | After checkout, customers can easily track the status of their orders, from processing to shipping to delivery. |
| Email Support | Users can send email to the admin for feedbacks and suggestions. |

## Dependencies
This web application relies on a stack of powerful and proven technologies to provide a seamless and feature-rich user experience. Here are the key dependencies that drive this platform:
| Dependency | Purpose |
| ------------- | ------------- |
| [Spring Boot](https://spring.io/projects/spring-boot) | Spring Boot forms the backbone of this application, offering a robust and efficient framework for building the server-side components of this e-commerce platform. It simplifies configuration, enhances productivity, and provides a wide range of tools for building enterprise-level applications. |
| [Spring Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)  | Spring Validation serves the essential purpose of ensuring that the data entered by users is valid, consistent, and adheres to predefined rules and constraints. |
|[Spring Security](https://spring.io/projects/spring-security)| Spring Security provides robust authentication, authorization, and protection against common web vulnerabilities, ensuring that only authorized users can access specific parts of the application and that user data remains secure. |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | The primary purpose of Spring Data JPA is to simplify database access and streamline the development of data-driven applications in Java. In this application, Spring Data JPA serves several crucial purposes to enhance and simplify the management of data and interactions within the database. |
| [MySQL](https://www.mysql.com/) | MySQL is the engine behind data storage and management. It enables this application to efficiently store and retrieve product information, user data, order details, and more. |
| [Bootstrap](https://getbootstrap.com) | Bootstrap allows this application to deliver a modern and visually appealing user interface. Its responsive design ensures this e-commerce platform looks and works seamlessly across various devices and screen sizes. It speeds up development, resulting in a more user-friendly and polished user experience. |
| [Start Boostrap](https://startbootstrap.com/) | This project's front-end UI is based on several Bootstrap templates created by [Start Boostrap](https://startbootstrap.com/). I want to express my gratitude to the developers for their fantastic work in designing the original template. Without their contribution, this project would not have been possible. | 
| [Thymeleaf](https://www.thymeleaf.org/) | Thymeleaf is a templating engine for server-side rendering in web applications, particularly well-suited for Java-based web frameworks like Spring. |
| [Lombok](https://projectlombok.org/) | Lombok is a library for Java that can significantly simplify and streamline your Java code by automatically generating common boilerplate code. |

## Credits
I would like to express my sincere gratitude to the following article and Youtube channels for their invaluable contributions and tutorials that have aided me in the development of this project:
- [Bro Code](https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ)
- [Chad Darby](https://www.youtube.com/channel/UC_c-e1vu4MBqOLY9WV1UrZw)
- [Selenium Express](https://www.youtube.com/@SeleniumExpress)
- [Java Techie](https://www.youtube.com/@Javatechie)
- [Laur Spilca](https://www.youtube.com/@laurspilca)
- [Baeldung](https://www.baeldung.com/)
- [Amigos Code](https://www.youtube.com/@amigoscode)

## Screenshots
### Database Schema
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/schema.jpg" width="90%"></img>

### Front Page
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/home.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/login.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/contact_us.jpg" width="23%"></img> 

### Admin Interface
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/dashboard.jpg" width="23%"></img>
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_users.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_user_one.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_categories.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_category_electronics.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_products.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_products_one.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_new_product.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/admin_orders.jpg" width="23%"></img> 

### Customer Interface
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_scategories1.jpg" width="23%"></img>
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_categories2.jpg" width="23%"></img>
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_product1" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_add_to_cart.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_checkout.jpg" width="23%"></img> 
<img src="https://github.com/mrkevr/e-commerce/blob/master/src/main/resources/static/img/user_orders.jpg" width="23%"></img> 
