package hymaia.benchmark

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
      inputFile <- sys.env.get("INPUT").toTry("input parameter not found")
      outputFile <- sys.env.get("OUTPUT").toTry("output parameter not found")
    } yield {
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

    val df = spark.read.schema(Tables.schemas(tableName)).option("delimiter", "|").csv(inputFile)
    df.write.mode(SaveMode.Overwrite).parquet(outputFile)
  }
}

