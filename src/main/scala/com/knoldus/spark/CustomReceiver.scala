package com.knoldus.spark

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.neo4j.driver.v1._

case class User(id: Int, name: String, salary: Int, age: Int)

class CustomReceiver(nodeName: String) extends Receiver[User](StorageLevel.MEMORY_AND_DISK_2) with Neo4jConstant {

  override def onStart(): Unit = {
    println("\n\nSpark Server Started with Neo4j")
    val thread = new Thread("Receiver") {
      override def run() {
        receive()
      }
    }
    thread.start()
  }

  override def onStop(): Unit = {
    println("\n\nSpark Server Stopped with Neo4j")
  }

  def receive() {
    try {
      val driver = GraphDatabase.driver(bolt_url, AuthTokens.basic(user_name, password))
      val session = driver.session
      val script = "MATCH (u:EMPINFO) RETURN u.id AS id, u.name AS name, u.age AS age, u.salary AS salary"
      val result: StatementResult = session.run(script)
      while (result.hasNext()) {
        val record = result.next()
        val data: User = User(id = record.get("id").asString.toInt, name = record.get("name").asString(),
          age = record.get("age").asString.toInt, salary = record.get("salary").asString.toInt)
        store(data)
      }
      session.close()
      driver.close()
      restart("\n\nTrying to connect again with server")
    } catch {
      case e: java.net.ConnectException =>
        restart("\n\nError connecting to " + host_name + ":" + port_number, e)
      case t: Throwable =>
        restart("\n\nError receiving data", t)
    }
  }
}
