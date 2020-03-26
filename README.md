# B-service task

Implement a bank service, which supports different users and their accounts. On behalf of each user, 
it's possible to transfer(to external banks as well), deposit, withdraw money. Check balance and history 
of the transactions. Each month the bank gets money from users for the service. If there is no operation 
then the commission is 100 units. If operation volume exceeds 30000 units there is no commission. Between 
0 and 30000 commission is 200 units. Apart from that bank takes a commission for transfers to external 
banks 1 %. Bank admin should be able to see the month report.

Technical statements: You can use any JVM language and any framework. It's ok to use in-memory db. 
Tests required. Test framework can be any. Security is not required

### Prerequisites 
* installed java
* installed maven
``
java -version
openjdk version "13.0.2" 2020-01-14
OpenJDK Runtime Environment (build 13.0.2+8)
OpenJDK 64-Bit Server VM (build 13.0.2+8, mixed mode)
mvn -version
Apache Maven 3.6.3 (NON-CANONICAL_2019-11-27T20:26:29Z_root)
Maven home: /opt/maven
Java version: 13.0.2, vendor: N/A, runtime: /usr/lib/jvm/java-13-openjdk
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.5.9-arch1-2", arch: "amd64", family: "unix"
``

### Run
``
mvn clean test spring-boot:run
``

### Check out
* swagger API
``
http://localhost:8080/api/swagger-ui.html#/
``
* see also: resources/rest-api.http