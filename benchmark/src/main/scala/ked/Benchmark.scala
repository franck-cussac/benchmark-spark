package ked

import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.util._

object Benchmark {
    val APP_NAME = "spark-benchmark"

    implicit class OptionOps[A](opt: Option[A]) {
        def toTry(msg: String): Try[A] = {
        opt
            .map(Success(_))
            .getOrElse(Failure(new NoSuchElementException(msg)))
        }
    }

    def main(args: Array[String]): Unit = {
        implicit val spark = SparkSession.builder()
            .appName(APP_NAME)
            .getOrCreate()

        println(s"sys.env = ${sys.env}")

        val res = for {
            region <- sys.env.get("REGION").toTry("region parameter not found")
            access_key <- sys.env.get("SCW_ACCESS_KEY").toTry("access_key parameter not found")
            secret_key <- sys.env.get("SCW_SECRET_KEY").toTry("secret_key parameter not found")
            inputPath <- sys.env.get("INPUT").toTry("input parameter not found")
            outputPath <- sys.env.get("OUTPUT").toTry("output parameter not found")
            experimentName <- sys.env.get("EXPERIMENT_NAME").toTry("experiment_name parameter not found")
            queryMode <- sys.env.get("QUERY_MODE").toTry("query_mode parameter not found")
        } yield {
            spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", s"https://s3.$region.scw.cloud");
            spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", access_key)
            spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", secret_key)
            spark.sparkContext.hadoopConfiguration.set("mapreduce.fileoutputcommitter.algorithm.version", "2")
            start(experimentName, queryMode, inputPath, outputPath)
        }

        if (res.isFailure) {
            throw res.failed.get
        }
        else {
            println("Run with success !")
        }
        spark.stop()
    }

    def start(experimentName: String, queryMode: String, inputPath: String, outputPath: String)(implicit spark: SparkSession): Unit = {
        import spark.implicits._

        Tables.createAll(inputPath)
        val queries = queryMode match {
            case "simple" => Queries.simple
            case "tpcds" => Queries.tpcds1_4
            case _  => Seq()
        }

        val benchmarkResults = queries.map { case (name, query) => 
            println(s"execute query $name")
            val startTime = System.currentTimeMillis()
            spark.sql(query).write.mode(SaveMode.Overwrite).parquet(s"$outputPath/query_results/$name")
            val endTime = System.currentTimeMillis()
            (name, endTime - startTime)
        }

        benchmarkResults.toDF("query_name", "execution_time").coalesce(1).write.mode(SaveMode.Overwrite).csv(s"$outputPath/experiment/name=$experimentName")
  }
}
