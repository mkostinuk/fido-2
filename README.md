# HTTP-servar
## Before running the project, ensure that you have the following installed on your machine:

- Java 17 (or compatible version)
- Maven 3.6+ (for building the project)
- PostgreSQL (version 12 or higher recommended)
## In order to run this program you need:
- Create new postgresql database
- change in src/main/resources/application.properties properties(url,susername, password, secret)
- build the project (mvn clean install)
- run the application (mvn spring-boot:run OR java -jar target/fisdo-2-0.0.1-SNAPSHOT.jar
## EndPoints:
### ROLE: USER, ADMIN
### POST: /api/auth/signUp ({ "username", "name", "age", "password" }) - creates new User 
### POST: /api/auth/signIn ({"username", "password"}) - authentication
### GET:/users/id/{id} - returns user by id
### GET: /users/username/{username} - returns user by username
### PUT: /users/addFriend (param : username) - adds a user to an authorized user's friends
### GET: /users/myFriends - returns friends of authorized user
### GET: /users/friendsOf/{username} - returns friends of user with that username
### DELETE:  /users/deleteFriends (param: username) - deletes one authorized user's of friend
### PUT: /users ({ "username", "name", "age", "password" }) - updates information about authorized user
### ROLE: ADMIN
### GET: /admin/all - returns all users
### PUT: /admin/changeRole (param uuid, role(USER,ADMIN)) - changes role of user
### DELETE: /admin/{id} - delete user


