# spring-batch
spring batch poc application which reads from the h2 database and writes to a csv file.

Installation Guides:
  1. Import application as an existing maven project.
  2. Build the maven project.
  3. Start the application by running it as SpringBootApp.
  
 Once the application is up. 
 
 Access the h2-console via following link:
  http://localhost:8080/h2-console
  
 Once the console is open create a table with name 'USER' with two columns 'id' and 'name'.
 Insert into the table 'USER' some dummy values.
 
 Once the values are added start the batch using the following rest end points:
  http://localhost:8080/load
