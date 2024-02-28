This project is a simple "hello world" spring boot confluent kafka app.

To run it:
1) move application.properties.clean to application.properties and edit the values to point to your kafka server.
2) use ./mvnw spring-boot:run
3) navigate to localhost:8086
4) post some messages using the form, and see them poping up in the output window when they are consumed



