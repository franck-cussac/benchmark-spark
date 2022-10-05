package hymaia

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
        val res = for {
            inputPath <- sys.env.get("INPUT").toTry("input parameter not found")
            outputPath <- sys.env.get("OUTPUT").toTry("output parameter not found")
            experimentName <- sys.env.get("EXPERIMENT_NAME").toTry("experiment_name parameter not found")
            queryMode <- sys.env.get("QUERY_MODE").toTry("query_mode parameter not found")
            outputMode <- sys.env.get("OUTPUT_MODE").toTry("output_mode parameter not found")
        } yield {
            implicit val spark: SparkSession = SparkSession.builder()
                .appName(APP_NAME)
                .getOrCreate()

            val r = Try(
                queryMode match {
                    case "simple" => Tables.createSimple(inputPath)
                    case "test" => Tables.createTest(inputPath)
                    case _ => Tables.createAll(inputPath)
                }
            ).map{ _ =>
                start(experimentName, queryMode, outputMode, inputPath, outputPath)
            }
            spark.stop()

            r.get
        }

        if (res.isFailure) {
            throw res.failed.get
        }
        else {
            println("Run with success !")
        }
    }

    def start(experimentName: String, queryMode: String, outputMode: String, inputPath: String, outputPath: String)(implicit spark: SparkSession): Unit = {
        import spark.implicits._

        val queries = queryMode match {
            case "simple" => Queries.simple
            case "tpcds" => Queries.tpcds1_4
            case "test" => Queries.test
            case _  => Seq()
        }

        val benchmarkResults = queries.map { case (name, query) => 
            println(s"execute query $name")
            val startTime = System.currentTimeMillis()
            val result = spark.sql(query)

            val output = outputMode match {
                case "parquet" => result.write.mode(SaveMode.Overwrite).parquet(s"$outputPath/query_results/$name")
                case "none" => result.foreach { _ => ():Unit }
            }
            
            val execTime = System.currentTimeMillis() - startTime
            println(s"$name finished in $execTime ms")
            (name, execTime)
        }

        benchmarkResults.toDF("query_name", "execution_time").coalesce(1).write.mode(SaveMode.Overwrite).csv(s"$outputPath/experiment/name=$experimentName")
  }
}
