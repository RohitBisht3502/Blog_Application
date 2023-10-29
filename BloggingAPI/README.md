# <h1 align = "center">  Application API </h1>
___ 
<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-4.0-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.1.3-brightgreen.svg" />
</a>
    <img alt = "License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
    </a>
</p>


---

<p align="left">

## Overview

The Blogging Management Application is a Spring Boot-based web application designed to help manage user,comment,post information. It allows users to perform various operations related to blogging management, including adding new comments and post, updating comments and post, and deleting comments and post from the system. When user sign in the system a token value for authentication is send to there registered email for confirmation of the user. And that token value is important while performing any task in system.

## Technologies Used

- **Framework:** Spring Boot
- **Language:** Java
- **Build Tool:** Maven
- **Cloud Services:** Amazon Web Services (AWS)
- **Database:** MySql
## Data Flow

### Controller

The Controller layer is responsible for handling incoming HTTP requests and delegating them to the appropriate services. It defines API endpoints for the following operations:

1. **Post User or User Sign Up:** `POST /users`
   
   This endpoint creates a user it is like as sign up in the system.

   ```java
   @PostMapping("users")
   public String userSignUp(@RequestBody User newUser) {
       
   }
   ```

2. **User Sign In:** `Sign In /users`
   
   This endpoint received user email and password for sign in the system .

   ```java
   @PostMapping("users")
   public String userSignIn(@RequestBody User newUser) {
       
   }
   ```

3. **Add Post:** `POST /blog post`
   This endpoint adds a new post to the system and also recieved user email and token value for verify authentication.

   ```java
   @PostMapping("BlogPost")
   public String createPost(@RequestParam String email,@RequestParam String tokenValue, @RequestBody Post instaPost){
     
    }
   ```

4. **Get Post:** `GET /Post Content`

   This endpoint get all the post by its ID.

   ```java
   @GetMapping("BlogPost/{postId}")
   public String getPostContentByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId){
    }
   ```

5. **Update Post Location:** `PUT /post/id/{id}/location/{location}`

   This endpoint updates the location of a post by its ID and needs a authentication for apply the changes in any task.

   ```java
   @PutMapping("BlogPost/Location/{location}/{postId}")
   public String updatePostLocationByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long 
   postId,@PathVariable String location){

    }

   ```

6. **Delete Post:** `DELETE /Post/{id}`

   This endpoint deletes a post by its ID.

   ```java
   @DeleteMapping("post/{id}")
   public String deletePost(@PathVariable Integer id) {
       
   }
   ```
7. **Add Comment:** `POST /Comment`
   This endpoint adds a new post to the system and also recieved user email and token value for verify authentication.

   ```java
   @PostMapping("comment/post/{postId}")
   public String addComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId,@RequestBody String 
   commentBody ){
       
    }
   ```

8. **Get Comment:** `GET /Comment`

   This endpoint get all the comments by its post ID.

   ```java
   @GetMapping("comment/{postId}")
    public List<String> getCommentByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId){
      
    }
   ```

9. **Update Comment Body:** `PUT /post/id/{id}/{newComment}`

   This endpoint updates the comment body of a post by its ID and needs a authentication for apply the changes in any task.

   ```java
   @PutMapping("Edit/Comment/{commentId}/{newComment}")
   public String editCommentByCommentId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long 
   commentId,@PathVariable String newComment){
        return userService.editCommentByCommentId(email,tokenValue,commentId,newComment);
    }

   ```

10. **Delete Comment:** `DELETE /comment/{id}`

    This endpoint deletes a comment from post by its ID.

    ```java
    @DeleteMapping("comment/{id}")
    public String deleteComment(@PathVariable Integer id) {
       
    }
     ```
### Services

The Services layer implements the core business logic, data processing, and interaction with the data repository. Key responsibilities include:

- Validating input data.
- Performing CRUD operations on restaurant data.
- Handling data transformations and interactions with external systems (if applicable).

### Repository

The Repository layer manages data access to the underlying database. It handles database operations such as Create, Read, Update, and Delete (CRUD) for restaurant data. Additionally, it may include data mapping and conversion between Java objects and database entities.

## Data Structures Used

1.ArrayList

The project utilizes the following data structures:

### User Class

The `User` class defines the structure for user data and includes the following fields:

- userId (Type: Long): An identifier for the user.
- userName (Type: String): The user's full name.
- userHandle (Type: String): A unique handle or username associated with the user, often used for identification or public display.
- userEmail (Type: String): The email address associated with the user's account.
- userPassword (Type: String): The password used for user authentication and access control.
- userGender (Type: Gender): A variable representing the user's gender, which may be defined as an enumeration or a custom data type.

### Post Class

The `Post` class defines the structure for post data and includes the following fields:

- postId (Type: Integer): An identifier for the post.
- postContent (Type: String): The content or text of the post.
- postCaption (Type: String): A caption associated with the post.
- postLocation (Type: String): The location where the post was created or is associated with.
- postType (Type: PostType): A variable of type PostType that likely represents the type or category of the post.
- postCreatedTimeStamp (Type: LocalDateTime): A timestamp indicating when the post was created or published.
- postOwner (Type: User): An association with a "User" object, presumably representing the owner or author of the post.
  
### Comment Class

The `Comment` class defines the structure for comment data and includes the following fields:

- commentId (Type: Integer): An identifier for the comment.
- commentBody (Type: String): The main content or text of the comment, which is marked as non-nullable.
- commentCreationTimeStamp (Type: LocalDateTime): A timestamp indicating when the comment was created.

### Type Enum

The `Gender Type` enum enumerates the possible restaurant types:

- `MALE`: 
- `FEMALE`: 
- `TRANS`: 
- ...

The `Post Type` enum enumerates the possible restaurant types:

- `IMAGE`: 
- `VIDEO`: 
- `TEXT`: 
- ...
### Mapping

The `Comment class` mappings are likes Many post have one comment and Many user can comment in post:

- @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post post;

- @ManyToOne
    @JoinColumn(name = "fk_commenter_id")
    private User commenter;

The `Post class` mappings are likes:

- @ManyToOne
    @JoinColumn(name = "fk_owner_user_id")
    private User postOwner;

### MySql

The project utilizes the `MYSql` database to store and manage lists of `users` ,`post`,`comment` objects in various parts of the application. `MySql` provides permanent storage and efficient element retrieval, making it suitable for storing blog records and performing operations on them.

These database enable the application to organize and manipulate blog data efficiently while maintaining data integrity.

## Project Summary

A Blogging API System in Spring Boot is a web application that allows users to create, read, update, and delete blog posts through a set of RESTful APIs. Spring Boot, a popular Java framework, is used to streamline the development of such systems. Here's a summary of the key features and components typically found in a Blogging API System built with Spring Boot:

Key Features:

- RESTful API endpoints for blog management.
- Data validation to ensure data integrity.
- Clean code separation with a layered architecture (Controller, Services, Repository).
- Robust error handling for improved reliability.
- Java-based backend and Maven for build management.

The Blogging Management Application serves as a practical example of Spring Boot application development, demonstrating best practices in API design and user data management. It offers a solid foundation for building and extending blogging management systems in various applications.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

Thank you to the Spring Boot and Java communities for providing excellent tools and resources.

## Contact
For questions or feedback, please contact [Rohit singh bisht](mailto:business.rohitbisht3502@gmail.com)
