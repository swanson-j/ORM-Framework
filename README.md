# ORM-Framework
Custom object relational mapping framework. Simplified, SQL-free interaction with the relational data source.


### Tech stack
* __Java 8__
* __Apache Maven__
* __PostGreSQL deployed on AWS RDS__
* __Git SCM__

### Getting Started
> Clone the repository(git clone https://github.com/swanson-j/ORM-Framework.git)

> Install the project with Maven(mvn install)

> Add the project as a dependency within your pom.xml

* User creates connection to DB with JDBC(Can also choose to create connection pool)

### Annotations
| Annotation  | Description |
| ----------- | ------------|
| Entity  | Placed above classes that will be persisted. Represents DB relation |
| Primary | Placed above the primary field. Represents primary key |
| Foreign | Placed above fields that are other user defined classes. Represents foreign key |
| Column  | Placed above fields that will persist. Represent column names within a relation |


