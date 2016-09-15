name := """neo-spark-streaming"""

version := "1.0"

val neo4j_driver = "org.neo4j.driver" % "neo4j-java-driver" % "1.0.4"
val spark = "org.apache.spark" %% "spark-core" % "2.0.0"
val ne = "org.neo4j" % "neo4j" % "3.0.4"
val nsp = "neo4j-contrib" % "neo4j-spark-connector" % "2.0.0-M1"
val graphX = "org.apache.spark" %% "spark-graphx" % "2.0.0"
val streaming = "org.apache.spark" % "spark-streaming_2.11" % "2.0.0"

lazy val commonSettings = Seq(
  organization := "com.knoldus.neo4j",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "neo4j_spark",
    libraryDependencies ++= Seq(neo4j_driver, spark, ne, nsp, graphX, streaming)
  )
