package devoxx.benchmark

import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.util._

object RawToParquet {
  implicit class OptionOps[A](opt: Option[A]) {
    def toTry(msg: String): Try[A] = {
      opt
        .map(Success(_))
        .getOrElse(Failure(new NoSuchElementException(msg)))
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val spark = SparkSession.builder().getOrCreate()

    spark.sparkContext.getConf.getAll.foreach(println)

    val res = for {
      region <- sys.env.get("REGION").toTry("region parameter not found")
      access_key <- sys.env.get("SCW_ACCESS_KEY").toTry("access_key parameter not found")
      secret_key <- sys.env.get("SCW_SECRET_KEY").toTry("secret_key parameter not found")
      inputFile <- sys.env.get("INPUT").toTry("input parameter not found")
      outputFile <- sys.env.get("OUTPUT").toTry("output parameter not found")
    } yield {
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", s"https://s3.$region.scw.cloud");
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", access_key)
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", secret_key)
      rawToParquet(inputFile, outputFile, inputFile.split('/').last)
    }
    if (res.isFailure) {
      throw res.failed.get
    }
    else {
      println("Run with success !")
    }
    spark.stop()
  }

  def rawToParquet(inputFile: String, outputFile: String, tableName: String)(implicit spark: SparkSession): Unit = {
    println(s"input = $inputFile")
    println(s"output = $outputFile")

    val raw = spark.read.schema(Tables.schemas(tableName)).option("delimiter", "|").csv(s"s3a://datalake-spark-benchmark/data/TPC-DS/10TB/$tableName")
    val customRaw = spark.read.schema(Tables.schemas(tableName)).option("delimiter", "|").csv(s"s3a://datalake-benchmark-spark/data/raw/TPC-DS/10TB/$tableName")
    val clean = spark.read.parquet(s"s3a://datalake-benchmark-spark/data/clean/TPC-DS/10TB/$tableName")

    println(s"raw data has ${raw.count()} lines")
    println(s"customRaw data has ${customRaw.count()} lines")
    println(s"clean data has ${clean.count()} lines")
  }
}

