### Running App
`mvn spring-boot:run`
`mvn spring-boot:run "-Drun.jvmArguments=-Dspring.profiles.active=test,secrets -Dspring.config.location=/Users/sbollam/app-location/secret-props/test/"`
The above command reads application-test.properties from src/main/resources folder and application-secrets.properties 
from specified spring.config.location value.

### MySQL
mysql ignores case
select * from table where name='john' and select * from table where name = 'JoHn'
work similarly as mySql is case insenstive by default.

MAC: mysql.server start | stop | restart | reload | force-reload | status
Linux: sudo /etc/init.d/mysql start

### Advanced rest client
Sending a get request to get orders

http://localhost:8080/order/getOrders?from=12/12/2017&to=12/18/2017&id=idididididid

### spring boot configurable app-properties url

https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html

### Spring data JPA reference documentation

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

