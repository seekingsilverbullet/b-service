# B-service task

Implement a bank service, which supports different users and their accounts. On behalf of each user, 
it's possible to transfer(to external banks as well), deposit, withdraw money. Check balance and history 
of the transactions. Each month the bank gets money from users for the service. If there is no operation 
then the commission is 100 units. If operation volume exceeds 30000 units there is no commission. Between 
0 and 30000 commission is 200 units. Apart from that bank takes a commission for transfers to external 
banks 1 %. Bank admin should be able to see the month report.

Technical statements: You can use any JVM language and any framework. It's ok to use in-memory db. 
Tests required. Test framework can be any. Security is not required
