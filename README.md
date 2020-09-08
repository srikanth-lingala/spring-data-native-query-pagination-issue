### Issue explanation:

As explained in [this stackoverflow issue][1], when running native query with Pagination, the result type object differs
depending on the page number. For page 0 (first page), the results are of type List of BigDecimals, and from the second
page on, results are of type List of Objects, with each Object having two BigDecimals, the first of which is the value
from the database row, and the second BigDecimal seems to be the row number. The two screenshots below show the 
difference in the result types:

Screenshot with list of BigDecimals

<img src="https://i.stack.imgur.com/5VS5i.png" width="300" height="120">


Screenshot with list of Objects

<img src="https://i.stack.imgur.com/MoQrZ.png" width="300" height="190">

This issue seems to be happening only with Oracle database. When I use H2, it seems to work fine, i.e, I get list of 
BigDecimals always no matter the page number.

### Steps to reproduce the issue:

Skip steps 1 and 2, if you have access to an instance of oracle available. In this case, make sure to change database 
configuration in application.properties

1. Download and install Docker
2. Start an instance of Oracle locally: `docker run -d --shm-size=2g -p 1521:1521 -p 8080:8080 orangehrm/oracle-xe-11g`
3. Run Application.java

You will see this exception in the console output:

> Caused by: java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class java.math.BigDecimal ([Ljava.lang.Object; and java.math.BigDecimal are in module java.base of loader 'bootstrap')
  	at java.base/java.util.stream.ReferencePipeline$5$1.accept(ReferencePipeline.java:229)

This happens because of the conversion of BigDecimal to long in Application.java. This shows that the result type was 
not of type List of BigDecimals as was the case on the first page.

[1]: https://stackoverflow.com/q/63738889/2736153
[proper-results-screenshot]: https://i.stack.imgur.com/5VS5i.png
[issue-screenshot]: https://i.stack.imgur.com/MoQrZ.png