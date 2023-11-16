# E-Commerce
A simple monolithic e-commerce web application developed using Spring Framework, MySQL, and Bootstrap.

- [Introduction](#introduction)
- [Features](#features)
    - [Admin Features](#admin-features)
    - [Customer Features](#customer-features)
- [Dependencies](#dependencies)
- [How To Run](#how-to-run)
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

## How To Run
1. Running this application requires the tools below.

| Tool | Link |
| ------------- | ------------- |
| [MySQL](https://dev.mysql.com/downloads/mysql) | [Installation Guide](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/) |
| [Maven 3](https://maven.apache.org/download.cgi) | [Installation Guide](https://phoenixnap.com/kb/install-maven-windows) |
| [Java 17](https://www.oracle.com/java/technologies/downloads/#java8](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)) | [Installation Guide](https://phoenixnap.com/kb/install-java-windows) |
| [Google Account](https://accounts.google.com) | [Oauth2 Guide](https://rohankadam965.medium.com/how-to-implement-oauth2-login-using-google-part-1-d57f9563b6b9) |
| [Github Account](https://github.com/join) | [Oauth2 Guide](https://www.youtube.com/watch?v=a-4LK0FiqNQ) |
> [!NOTE]  
> Google and Github account will be used for OAuth2 Single Sign-On of the users. Google Mail will be used for sending emails.

2. Clone this repository
3. Go to e-commerce/src/main/resources/application.yml and replace the placeholders (enclosed by <>) with your own credentials
MySQL
```
datasource:
    url: <url>
    username: <username>
    password: <password>
    driver-class-name: com.mysql.cj.jdbc.Driver
```
Google and Github
```
 security:
    oauth2:
      client:
        registration:
          github:
            clientId: <github-id>
            clientSecret: <github-secret>
          google:
            clientId: <google-id>
            clientSecret: <google-secret>
```
Gmail
```
mail:
    host: smtp.gmail.com
    port: 587
    username: <google-username>
    password: <google-password>
    default-encoding: UTF-8
    properties:
      mail:
        mime:
          charset: UTF
        smtp:
          writetimeout: 10000
          connectiontimeout: 10000
          timeout: 10000
          auth: true
          starttls:
            enable: true
            required: true
```
4. Go to e-commerce/src/main/resources/schema.sql, copy the script and run it on your MySQL database
5. On the application directory, build the project by running mvn clean package or mvn clean install
```
C:\Your\Directory\e-commerce>mvn clean package
...
...
...
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  10.654 s
[INFO] Finished at: 2023-11-16T13:20:40.804+08:00
[INFO] ------------------------------------------------------------------------
```
6. Once successfully built, you can run the jar file on the target folder using this command :
```
C:\Your\Directory\e-commerce\target>java -jar e-commerce-0.0.1-SNAPSHOT.jar
```
> [!NOTE]  
> The application will run on default port, to specify your desired port, use this command :
```
C:\Your\Directory\e-commerce\target>java -jar e-commerce-0.0.1-SNAPSHOT.jar --server-port=<your_desired_port>
```
7. Once the application is live, you should see something like this :
```
2023-11-16T13:29:41.787+08:00  INFO 6460 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8086 (http) with context path '/e-commerce'
2023-11-16T13:29:41.804+08:00  INFO 6460 --- [main] d.mrkevr.ecommerce.ECommerceApplication  : Started ECommerceApplication in 6.492 seconds (process running for 7.008)
```
8. To see the web page, go to http://localhost:8086/e-commerce, just replace the port number with the one that you configured
9. To access the admin page, use the admin account created by default using these credentials :
```
username : admin
password : admin
```

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
