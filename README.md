# spark_assignment_3

## Prerequisites:

Neo4j database must have been installed on your system and the password can be set in the Neo4jConstant.scala file. If you have not downloaded it See the link [here](https://neo4j.com/download/)

After installing Neo4j run script which is placed in the resources from the Neo4j console.


----
LOAD CSV WITH HEADERS FROM 'file://$SRC_PATH/src/main/resources/employee.csv' AS row 
CREATE (t:Details {id: row.id, name:row.name, age: row.age, address:row.address, salary: row.salary}) return t;

$SRC_PATH => Where you keep the project.
Ex. => /home/anurag/spark_assignment_3

----
