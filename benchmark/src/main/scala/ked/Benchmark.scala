package ked

import org.apache.spark.sql.SparkSession

object Benchmark {
    val APP_NAME = "spark-benchmark"
    def main(args: Array[String]): Unit = {
        if (args.size != 3) {
            println("this app take 3 arguments; experimentName, queryMode, inputPath, outputPath")
            return
        }

        val experimentName = args(0)
        val queryMode = args(1)
        val inputPath = args(2)
        val outputPath = args(3)

        implicit val spark = SparkSession.builder()
            .appName(APP_NAME)
            .getOrCreate()
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
            spark.sql(query).write.mode("overwrite").parquet(s"$outputPath/query_results/$name")
            val endTime = System.currentTimeMillis()
            (name, endTime - startTime)
        }

        benchmarkResults.toDF("query_name", "execution_time").coalesce(1).write.mode("overwrite").csv(s"$outputPath/experiment/name=$experimentName")
  }
}
