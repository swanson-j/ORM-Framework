# ORM-Framework
Custom object relational mapping framework. Simplified, SQL-free interaction with the relational data source.


### Tech stack
* __Java 8__
* __Apache Maven__
* __PostGreSQL deployed on AWS RDS__
* __Git SCM__

### Getting Started
> Clone the repository(git clone https://github.com/swanson-j/ORM-Framework.git)
>
> Install the project with Maven(mvn install)
>
> Add the project as a dependency within your pom.xml

### How It Works
* User creates connection to DB with JDBC by creating a configuration file listing:
  > 1. DB url(ex: jdbc:postgresql://samplepsql.sample.us-east-1.rds.amazonaws.com:5432/postgres?currentSchema=yourSchema)
  > 
  > 2. username
  > 
  > 3. password
* Set the path of the Entity Manager singleton to the location of the configuration file(ex. EntityManager.setPath(src/main/resources/jdbc.config))
* Annotate all classes and fields that will be persisted with the appropriate Annotations
* You are now ready to use the Entity Manager to perform CRUD operations on objects

### Operations
| Operation | Description | Usage |
| --------- | ----------- | ----- |
| save      | Persists object to data store | EntityManager.save(Object)  |
| read      | Reads from data store and dynamically constructs parent object with all dependencies  | EntityManager.read(clazz.class, primaryId)  |
| update    | Updates object in data store if primary key exists  | EntityManager.update(Object, primaryId) |
| delete    | Deletes object from data store while maintaining referential integrity |  EntityManager.delete(clazz.class, primaryId)  |

### Annotations
| Annotation  | Description |
| ----------- | ------------|
| @Entity  | Placed above classes that will be persisted. Represents DB relation |
| @Primary | Placed above the primary field. Represents primary key |
| @Foreign | Placed above fields that are other user defined classes. Represents foreign key |
| @Column  | Placed above fields that will persist. Represent column names within a relation |


