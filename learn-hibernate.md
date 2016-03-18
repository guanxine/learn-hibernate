# Hibernate

##ORM(Object-Relational Mapping)

converting data between relational databases and object oriented programming languages

Java Objects<->ORM/Hibernate<->RDBMS

Java Application<->Persistent Object<->Hibernate(Hibernate.properties+XML Mapping)<->DB

Hibernate:

|Configuration|Session Factory|Session|Hibernate|
|-|-|-|-|
|Transaction|Query|Criteria|Hibernate|
|JTA|JDBC|JNDI|Use existing Java API|

* JTA:Java Transaction API
* JNDI:Java Naming and Directory Interface

### Configuration Object

* first create in any hibernate application
* created only once during application initialization
* represents a configuration or properties file(required)
* two keys components:
	1. Database Connection
		* hibernate.properties and hibernate.cfg.xml supported hibernate
	2. Class Mapping Setup
		* connection between the Java classes and database tables

### SessionFactory Object

*  Configuration object create
*  Inturn configures Hibernate using the supplied configuration file 
*  Allow Session Object be instantiated
*  Heavyweight:One database->A separate Configuration file->One SessionFactory Object(Thread safe object)

### Session Object

* get a physical connection with a database.
* lightweight:be instantiated each time an interaction is needed with the database
* Persistent objects are saved and retrieved 
* not be kept open for a long time,should be created and destroyed them as needed(not usually thread safe)
### Transaction Object

* a unit of work with the database 
* handled by an underlying transaction manager and transaction (from JDBC or JTA).
* optional object

### Query Object

* SQL Or HQL

### Criteria Object
* object oriented criteria queries 

## Required
* dom4j-XML parsing
* Xalan-XSLT Processor
* Xerces-Thr Xerces Java Parser
* cglib-Appropriate changes to Java classes at runtime
* log4j-Logging Faremwork 
* Commons-Logging, Email etc
* SLF4J- Logging Facade for Java

## Configuration

* a standard Java properties **hibernate.properties** or an XML file named **hibernate.cfg.xml**.
* hibernate.cfg.xml kept in the root directory of your application's classpath

### Hibernate Properties
* hibernate.dialect：generate the appropriate SQL for the chosen database.
* hibernate.connection.driver_class:The JDBC driver class.
* hibernate.connection.url:The JDBC URL to the database instance.
* hibernate.connection.username:The database username.
* hibernate.connection.password:The database password.
* hibernate.connection.pool_size:Limits the number of connections waiting in the Hibernate database connection pool
* hibernate.connection.autocommit:Allows autocommit mode to be used for the JDBC connection.

**databases dialect**:

|Database|Dialect Property|
|-|-|
|DB2|org.hibernate.dialect.DB2Dialect|
|HSQLDB	|org.hibernate.dialect.HSQLDialect|
|HypersonicSQL|	org.hibernate.dialect.HSQLDialect|
|Informix|	org.hibernate.dialect.InformixDialect|
|Ingres	|org.hibernate.dialect.IngresDialect|
|Interbase|	org.hibernate.dialect.InterbaseDialect|
|Microsoft SQL Server 2000	|org.hibernate.dialect.SQLServerDialect|
|Microsoft SQL Server 2005	|org.hibernate.dialect.SQLServer2005Dialect|
|Microsoft SQL Server 2008	|org.hibernate.dialect.SQLServer2008Dialect|
|MySQL	|org.hibernate.dialect.MySQLDialect|
|Oracleno representation in the database and no identifier value (any version)	|org.hibernate.dialect.OracleDialect|
|Oracle 11g	|org.hibernate.dialect.Oracle10gDialect|
|Oracle 10g|	org.hibernate.dialect.Oracle10gDialect|
|Oracle 9i	|org.hibernate.dialect.Oracle9iDialect|
|PostgreSQL	|org.hibernate.dialect.PostgreSQLDialect|
|Progress	|org.hibernate.dialect.ProgressDialect|
|SAP DB	|org.hibernate.dialect.SAPDBDialect|
|Sybase|	org.hibernate.dialect.SybaseDialect|
|Sybase Anywhere	|org.hibernate.dialect.SybaseAnywhereDialect|

## Sessions

* offer create, read and delete operations for instances of mapped entity classes.

### Instances three states

* transient
	1. not associated with a Session
	2. no representation in the database and no identifier value
* persistent
	1. associated with a Session
	2. representation in the database and identifier value
* detached
	1. Once we close the Hibernate Session, the persistent instance will become a detached instance
	
## Persistent Class
* a simple POJO(Plain Old Java Object)

## Mapping

### Files

### Types

* Primitive types:

	|Mapping type	|Java type|	ANSI SQL Type|
|-|-|-|
|integer|	int or java.lang.Integer|	INTEGER|
|long	|long or java.lang.Long	|BIGINT|
|short	|short or java.lang.Short|	SMALLINT|
|float	|float or java.lang.Float|	FLOAT|
|double	|double or java.lang.Double	|DOUBLE|
|big_decimal	|java.math.BigDecimal	|NUMERIC|
|character|	java.lang.String	|CHAR(1)|
|string|	java.lang.String|	VARCHAR|
|byte|	byte or java.lang.Byte	|TINYINT|
|boolean|	boolean or java.lang.Boolean|	BIT||
|yes/no	|boolean or java.lang.Boolean	|CHAR(1) ('Y' or 'N')
|true/false|	boolean or java.lang.Boolean|	CHAR(1) ('T' or 'F')|

* Date and time types

	|Mapping type	|Java type|	ANSI SQL Type|
|-|-|-|
|date|	java.util.Date or java.sql.Date	|DATE|
|time|	java.util.Date or java.sql.Time	|TIME|
|timestamp	|java.util.Date or java.sql.Timestamp|TIMESTAMP|
|calendar|	java.util.Calendar	|TIMESTAMP|

* Binary and large object types



	|Mapping type	|Java type|	ANSI SQL Type|
|-|-|-|
|binary|	byte[]|	VARBINARY (or BLOB)
|text	|java.lang.String	|CLOB|
|serializable	|any Java class that implements java.io.Serializable|VARBINARY (or BLOB)|
|clob|	java.sql.Clob	|CLOB|
|blob|	java.sql.Blob	|BLOB|
|calendar_date	|java.util.Calendar|	DATE|

## O/R Mappings
### Collections Mappings:

###Association Mappings:

##### Many-to-One:
Employee->Address

```
 <many-to-one name="address" column="address"
                     class="cn.gx.model.many2one.Address" not-null="true"/>
```

##### One-to-One:
Employee->Address

```
 <many-to-one name="address" column="address"  unique="true"
                     class="cn.gx.model.many2one.Address" not-null="true"/>
```

##### One-to-Many:

Employee->Certificates:

* `name` Set variable in the parent class(certificates)
* `<key>` the column in the CERTIFICATE table that holds the foreign key to the parent object(EMPLOYEE)
* `<one-to-many>` indicates that one Employee object relates to many Certificate objects

```

 <set name="certificates" cascade="all">
         <key column="employee_id"/>
         <one-to-many class="Certificate"/>
 </set>
 
```

##### Many-to-Many:

* `name`：set to the defined Set variable in the parent class（certificates）
* `table`:the intermediate table 
* `<key>`: the column in the **EMP_CERT** table that holds the foreign key to the parent object(EMPLOYEE) and links to the **certification_id** in the CERTIFICATE table
* `<many-to-many>` :one Employee object relates to many Certificate objects and `column` attributes are used to link intermediate EMP_CERT

```
<set name="certificates" cascade="save-update" table="EMP_CERT">
         <key column="employee_id"/>
         <many-to-many column="certificate_id" class="Certificate"/>
      </set>
```

##Annotations

##Hibernate - Query Language
