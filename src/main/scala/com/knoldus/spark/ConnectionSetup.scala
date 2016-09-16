package com.knoldus.spark

import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}


object ConnectionSetup extends App with Neo4jConstant{

  val conf: SparkConf = new SparkConf().setAppName("Neo4j-Spark-Streaming").setMaster("local[4]")
  val sc: SparkContext = new SparkContext(conf)
  val streamingContext = new StreamingContext(sc, Seconds(5))
  val receiverStream: ReceiverInputDStream[User] = streamingContext.receiverStream(new CustomReceiver(node_name))

  receiverStream.foreachRDD(user => println("\n\nuser >>> " + user.count() + "\n\n"))
  receiverStream.foreachRDD(user => println("\n\n user information data : " + user.foreach(usr => println(usr)) + " \n\n"))

  streamingContext.start()
  streamingContext.awaitTermination()
}
