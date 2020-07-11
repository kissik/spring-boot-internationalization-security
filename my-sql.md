[![Awesome][icon-awesome]][awesome]
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

# MySQL  
1. [Download](https://dev.mysql.com/downloads/mysql/) MySQL server  
1. Install MySQL server  
1. Start MySQL server  
1. run [schema.sql](src/main/resources/mysql/schema.sql)  
1. run [data.sql](src/main/resources/mysql/data.sql)  
1. Configure [application.properties](src/main/resources/application.properties):  
```spring.datasource.url=jdbc:mysql://```**localhost:3306**```/workshop?useUnicode=yes&characterEncoding=UTF-8```  
```spring.datasource.user=```**user**  
```spring.datasource.password=```**password**  

⤴️ Back to [Spring Workshop](../..)  

[icon-awesome]: https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg
[awesome]: https://github.com/sindresorhus/awesome
