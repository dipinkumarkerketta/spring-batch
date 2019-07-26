# spring-batch
spring batch poc application which reads from csv and writes to h2 database

Installation Guides:
  1. Import application as an existing maven project.
  2. Build the maven project.
  3. Start the application by running it as SpringBootApp.
  
 Once the application is up. 
 A scheduler is scheduled to insert the data into the H2 database after every second.
 
 To see the database and perform queries open the following url:
  http://localhost:8080/h2-console
  
 A rest endpoint is also exposed inorder to manually start the batch process, inorder
 to do so open the following url:
  http://localhost:8080/load
