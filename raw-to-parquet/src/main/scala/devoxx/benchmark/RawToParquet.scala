package devoxx.benchmark

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
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

    println(s"sys.env = ${sys.env}")

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
      spark.sparkContext.hadoopConfiguration.set("mapreduce.fileoutputcommitter.algorithm.version", "2")
      rawToParquet(inputFile, outputFile)
    }
    if (res.isFailure) {
      throw res.failed.get
    }
    else {
      println("Run with success !")
    }
    spark.stop()
  }

  def rawToParquet(inputFile: String, outputFile: String)(implicit spark: SparkSession): Unit = {
    println(s"input = $inputFile")
    println(s"output = $outputFile")

    val df = spark.read.option("delimiter", "|").csv(inputFile)
    df.write.mode(SaveMode.Overwrite).parquet(outputFile)
  }
}

