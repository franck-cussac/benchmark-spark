package ked

import org.apache.spark.sql.{DataFrame, SparkSession}

object Tables {
    val allTableName = Seq(
        "call_center",
        "catalog_page",
        "catalog_returns",
        "catalog_sales",
        "customer",
        "customer_address",
        "customer_demographics",
        "date_dim",
        "household_demographics",
        "income_band",
        "inventory",
        "item",
        "promotion",
        "reason",
        "ship_mode",
        "store",
        "store_returns",
        "store_sales",
        "time_dim",
        "warehouse",
        "web_page",
        "web_returns",
        "web_sales",
        "web_site",
    )
    
    val tableName = Seq(
        "catalog_returns",
        "catalog_sales",
        "inventory",
        "store_returns",
        "store_sales",
        "web_returns",
        "web_sales"
    )

    def createAll(inputPath: String)(implicit spark: SparkSession): Unit = {
        tableName.foreach(read(inputPath))
    }

    def read(path: String)(tableName: String)(implicit spark: SparkSession): Unit = {
        spark.read.parquet(s"$path/$tableName").createOrReplaceTempView(tableName)
    }
}
