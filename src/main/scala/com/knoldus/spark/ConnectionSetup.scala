package com.knoldus.spark

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by anurag on 16/9/16.
 */

object ConnectionSetup extends App with Neo4jConstant {

  val conf: SparkConf = new SparkConf().setAppName("Neo4j-Spark-Streaming").setMaster("local[4]")
  val sc: SparkContext = new SparkContext(conf)
  val streamingContext = new StreamingContext(sc, Seconds(10))
  val receiverStream: ReceiverInputDStream[User] = streamingContext.receiverStream(new CustomReceiver(node_name))

  receiverStream.foreachRDD(user => println("\n\n user information data : " + user.foreach(usr => println(usr.name)) + " \n\n"))

  val userName_count: DStream[(String, Int)] = receiverStream.map(usr => (usr.name, 1))
  val wc = userName_count.reduceByKey(_ + _)
  wc.foreachRDD { data =>
    println("User Name  with count >> " + data.collect().mkString(",\n"))
  }

  streamingContext.start()
  streamingContext.awaitTermination()
}
