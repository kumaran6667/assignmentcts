This project is created using spring boot 2.
import the project in eclipse
right click project and give run as maven install.
once maven install is completed,
run the class RaboBankApplication or right click the project --> run as spring boot app
use postman as webservice client to give the request to webservice
open postman app
select http method as post and give this url http://localhost:8080/rabobank/processStatment
click header in postman
-----------------------
key      value
---      -----
Accept : application/json
click on Body and select form-data
----------------------------------
key      value
---      -----
file : (attach your input file here either csv or xml)

finally click on send 
