package devoxx.benchmark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object Tables {
    val tableName = Seq(
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

    val schemas = Map(
    "catalog_sales" -> StructType(Seq(
      StructField("cs_sold_date_sk",          IntegerType),
      StructField("cs_sold_time_sk",          IntegerType),
      StructField("cs_ship_date_sk",          IntegerType),
      StructField("cs_bill_customer_sk",      IntegerType),
      StructField("cs_bill_cdemo_sk",         IntegerType),
      StructField("cs_bill_hdemo_sk",         IntegerType),
      StructField("cs_bill_addr_sk",          IntegerType),
      StructField("cs_ship_customer_sk",      IntegerType),
      StructField("cs_ship_cdemo_sk",         IntegerType),
      StructField("cs_ship_hdemo_sk",         IntegerType),
      StructField("cs_ship_addr_sk",          IntegerType),
      StructField("cs_call_center_sk",        IntegerType),
      StructField("cs_catalog_page_sk",       IntegerType),
      StructField("cs_ship_mode_sk",          IntegerType),
      StructField("cs_warehouse_sk",          IntegerType),
      StructField("cs_item_sk",               IntegerType),
      StructField("cs_promo_sk",              IntegerType),
      StructField("cs_order_number",          LongType),
      StructField("cs_quantity",              IntegerType),
      StructField("cs_wholesale_cost",        FloatType),
      StructField("cs_list_price",            FloatType),
      StructField("cs_sales_price",           FloatType),
      StructField("cs_ext_discount_amt",      FloatType),
      StructField("cs_ext_sales_price",       FloatType),
      StructField("cs_ext_wholesale_cost",    FloatType),
      StructField("cs_ext_list_price",        FloatType),
      StructField("cs_ext_tax",               FloatType),
      StructField("cs_coupon_amt",            FloatType),
      StructField("cs_ext_ship_cost",         FloatType),
      StructField("cs_net_paid",              FloatType),
      StructField("cs_net_paid_inc_tax",      FloatType),
      StructField("cs_net_paid_inc_ship",     FloatType),
      StructField("cs_net_paid_inc_ship_tax", FloatType),
      StructField("cs_net_profit",            FloatType)
    )),
    "catalog_returns" -> StructType(Seq(
      StructField("cr_returned_date_sk",      IntegerType),
      StructField("cr_returned_time_sk",      IntegerType),
      StructField("cr_item_sk",               IntegerType),
      StructField("cr_refunded_customer_sk",  IntegerType),
      StructField("cr_refunded_cdemo_sk",     IntegerType),
      StructField("cr_refunded_hdemo_sk",     IntegerType),
      StructField("cr_refunded_addr_sk",      IntegerType),
      StructField("cr_returning_customer_sk", IntegerType),
      StructField("cr_returning_cdemo_sk",    IntegerType),
      StructField("cr_returning_hdemo_sk",    IntegerType),
      StructField("cr_returning_addr_sk",     IntegerType),
      StructField("cr_call_center_sk",        IntegerType),
      StructField("cr_catalog_page_sk",       IntegerType),
      StructField("cr_ship_mode_sk",          IntegerType),
      StructField("cr_warehouse_sk",          IntegerType),
      StructField("cr_reason_sk",             IntegerType),
      StructField("cr_order_number",          LongType),
      StructField("cr_return_quantity",       IntegerType),
      StructField("cr_return_amount",         FloatType),
      StructField("cr_return_tax",            FloatType),
      StructField("cr_return_amt_inc_tax",    FloatType),
      StructField("cr_fee",                   FloatType),
      StructField("cr_return_ship_cost",      FloatType),
      StructField("cr_refunded_cash",         FloatType),
      StructField("cr_reversed_charge",       FloatType),
      StructField("cr_store_credit",          FloatType),
      StructField("cr_net_loss",              FloatType)
    )),
    "inventory" -> StructType(Seq(
      StructField("inv_date_sk",          IntegerType),
      StructField("inv_item_sk",          IntegerType),
      StructField("inv_warehouse_sk",     IntegerType),
      StructField("inv_quantity_on_hand", IntegerType)
    )),
    "store_sales" -> StructType(Seq(
      StructField("ss_sold_date_sk",      IntegerType),
      StructField("ss_sold_time_sk",      IntegerType),
      StructField("ss_item_sk",           IntegerType),
      StructField("ss_customer_sk",       IntegerType),
      StructField("ss_cdemo_sk",          IntegerType),
      StructField("ss_hdemo_sk",          IntegerType),
      StructField("ss_addr_sk",           IntegerType),
      StructField("ss_store_sk",          IntegerType),
      StructField("ss_promo_sk",          IntegerType),
      StructField("ss_ticket_number",     LongType),
      StructField("ss_quantity",          IntegerType),
      StructField("ss_wholesale_cost",    FloatType),
      StructField("ss_list_price",        FloatType),
      StructField("ss_sales_price",       FloatType),
      StructField("ss_ext_discount_amt",  FloatType),
      StructField("ss_ext_sales_price",   FloatType),
      StructField("ss_ext_wholesale_cost",FloatType),
      StructField("ss_ext_list_price",    FloatType),
      StructField("ss_ext_tax",           FloatType),
      StructField("ss_coupon_amt",        FloatType),
      StructField("ss_net_paid",          FloatType),
      StructField("ss_net_paid_inc_tax",  FloatType),
      StructField("ss_net_profit",        FloatType)
    )),
    "store_returns" -> StructType(Seq(
      StructField("sr_returned_date_sk",   IntegerType),
      StructField("sr_return_time_sk",     IntegerType),
      StructField("sr_item_sk",            IntegerType),
      StructField("sr_customer_sk",        IntegerType),
      StructField("sr_cdemo_sk",           IntegerType),
      StructField("sr_hdemo_sk",           IntegerType),
      StructField("sr_addr_sk",            IntegerType),
      StructField("sr_store_sk",           IntegerType),
      StructField("sr_reason_sk",          IntegerType),
      StructField("sr_ticket_number",      LongType),
      StructField("sr_return_quantity",    IntegerType),
      StructField("sr_return_amt",         FloatType),
      StructField("sr_return_tax",         FloatType),
      StructField("sr_return_amt_inc_tax", FloatType),
      StructField("sr_fee",                FloatType),
      StructField("sr_return_ship_cost",   FloatType),
      StructField("sr_refunded_cash",      FloatType),
      StructField("sr_reversed_charge",    FloatType),
      StructField("sr_store_credit",       FloatType),
      StructField("sr_net_loss",           FloatType)
    )),
    "web_sales" -> StructType(Seq(
      StructField("ws_sold_date_sk",          IntegerType),
      StructField("ws_sold_time_sk",          IntegerType),
      StructField("ws_ship_date_sk",          IntegerType),
      StructField("ws_item_sk",               IntegerType),
      StructField("ws_bill_customer_sk",      IntegerType),
      StructField("ws_bill_cdemo_sk",         IntegerType),
      StructField("ws_bill_hdemo_sk",         IntegerType),
      StructField("ws_bill_addr_sk",          IntegerType),
      StructField("ws_ship_customer_sk",      IntegerType),
      StructField("ws_ship_cdemo_sk",         IntegerType),
      StructField("ws_ship_hdemo_sk",         IntegerType),
      StructField("ws_ship_addr_sk",          IntegerType),
      StructField("ws_web_page_sk",           IntegerType),
      StructField("ws_web_site_sk",           IntegerType),
      StructField("ws_ship_mode_sk",          IntegerType),
      StructField("ws_warehouse_sk",          IntegerType),
      StructField("ws_promo_sk",              IntegerType),
      StructField("ws_order_number",          LongType),
      StructField("ws_quantity",              IntegerType),
      StructField("ws_wholesale_cost",        FloatType),
      StructField("ws_list_price",            FloatType),
      StructField("ws_sales_price",           FloatType),
      StructField("ws_ext_discount_amt",      FloatType),
      StructField("ws_ext_sales_price",       FloatType),
      StructField("ws_ext_wholesale_cost",    FloatType),
      StructField("ws_ext_list_price",        FloatType),
      StructField("ws_ext_tax",               FloatType),
      StructField("ws_coupon_amt",            FloatType),
      StructField("ws_ext_ship_cost",         FloatType),
      StructField("ws_net_paid",              FloatType),
      StructField("ws_net_paid_inc_tax",      FloatType),
      StructField("ws_net_paid_inc_ship",     FloatType),
      StructField("ws_net_paid_inc_ship_tax", FloatType),
      StructField("ws_net_profit",            FloatType)
    )),
    "web_returns" -> StructType(Seq(
      StructField("wr_returned_date_sk",      IntegerType),
      StructField("wr_returned_time_sk",      IntegerType),
      StructField("wr_item_sk",               IntegerType),
      StructField("wr_refunded_customer_sk",  IntegerType),
      StructField("wr_refunded_cdemo_sk",     IntegerType),
      StructField("wr_refunded_hdemo_sk",     IntegerType),
      StructField("wr_refunded_addr_sk",      IntegerType),
      StructField("wr_returning_customer_sk", IntegerType),
      StructField("wr_returning_cdemo_sk",    IntegerType),
      StructField("wr_returning_hdemo_sk",    IntegerType),
      StructField("wr_returning_addr_sk",     IntegerType),
      StructField("wr_web_page_sk",           IntegerType),
      StructField("wr_reason_sk",             IntegerType),
      StructField("wr_order_number",          LongType),
      StructField("wr_return_quantity",       IntegerType),
      StructField("wr_return_amt",            FloatType),
      StructField("wr_return_tax",            FloatType),
      StructField("wr_return_amt_inc_tax",    FloatType),
      StructField("wr_fee",                   FloatType),
      StructField("wr_return_ship_cost",      FloatType),
      StructField("wr_refunded_cash",         FloatType),
      StructField("wr_reversed_charge",       FloatType),
      StructField("wr_account_credit",        FloatType),
      StructField("wr_net_loss",              FloatType)
    )),
    "call_center" -> StructType(Seq(
      StructField("cc_call_center_sk",        IntegerType),
      StructField("cc_call_center_id",        StringType),
      StructField("cc_rec_start_date",        DateType),
      StructField("cc_rec_end_date",          DateType),
      StructField("cc_closed_date_sk",        IntegerType),
      StructField("cc_open_date_sk",          IntegerType),
      StructField("cc_name",                  StringType),
      StructField("cc_class",                 StringType),
      StructField("cc_employees",             IntegerType),
      StructField("cc_sq_ft",                 IntegerType),
      StructField("cc_hours",                 StringType),
      StructField("cc_manager",               StringType),
      StructField("cc_mkt_id",                IntegerType),
      StructField("cc_mkt_class",             StringType),
      StructField("cc_mkt_desc",              StringType),
      StructField("cc_market_manager",        StringType),
      StructField("cc_division",              IntegerType),
      StructField("cc_division_name",         StringType),
      StructField("cc_company",               IntegerType),
      StructField("cc_company_name",          StringType),
      StructField("cc_street_number",         StringType),
      StructField("cc_street_name",           StringType),
      StructField("cc_street_type",           StringType),
      StructField("cc_suite_number",          StringType),
      StructField("cc_city",                  StringType),
      StructField("cc_county",                StringType),
      StructField("cc_state",                 StringType),
      StructField("cc_zip",                   StringType),
      StructField("cc_country",               StringType),
      StructField("cc_gmt_offset",            FloatType),
      StructField("cc_tax_percentage",        FloatType)
    )),
    "catalog_page" -> StructType(Seq(
      StructField("cp_catalog_page_sk",       IntegerType),
      StructField("cp_catalog_page_id",       StringType),
      StructField("cp_start_date_sk",         IntegerType),
      StructField("cp_end_date_sk",           IntegerType),
      StructField("cp_department",            StringType),
      StructField("cp_catalog_number",        IntegerType),
      StructField("cp_catalog_page_number",   IntegerType),
      StructField("cp_description",           StringType),
      StructField("cp_type",                  StringType)
    )),
    "customer" -> StructType(Seq(
      StructField("c_customer_sk",             IntegerType),
      StructField("c_customer_id",             StringType),
      StructField("c_current_cdemo_sk",        IntegerType),
      StructField("c_current_hdemo_sk",        IntegerType),
      StructField("c_current_addr_sk",         IntegerType),
      StructField("c_first_shipto_date_sk",    IntegerType),
      StructField("c_first_sales_date_sk",     IntegerType),
      StructField("c_salutation",              StringType),
      StructField("c_first_name",              StringType),
      StructField("c_last_name",               StringType),
      StructField("c_preferred_cust_flag",     StringType),
      StructField("c_birth_day",               IntegerType),
      StructField("c_birth_month",             IntegerType),
      StructField("c_birth_year",              IntegerType),
      StructField("c_birth_country",           StringType),
      StructField("c_login",                   StringType),
      StructField("c_email_address",           StringType),
      StructField("c_last_review_date",        StringType)
    )),
    "customer_address" -> StructType(Seq(
      StructField("ca_address_sk",             IntegerType),
      StructField("ca_address_id",             StringType),
      StructField("ca_street_number",          StringType),
      StructField("ca_street_name",            StringType),
      StructField("ca_street_type",            StringType),
      StructField("ca_suite_number",           StringType),
      StructField("ca_city",                   StringType),
      StructField("ca_county",                 StringType),
      StructField("ca_state",                  StringType),
      StructField("ca_zip",                    StringType),
      StructField("ca_country",                StringType),
      StructField("ca_gmt_offset",             FloatType),
      StructField("ca_location_type",          StringType)
    )),
    "customer_demographics" -> StructType(Seq(
      StructField("cd_demo_sk",                IntegerType),
      StructField("cd_gender",                 StringType),
      StructField("cd_marital_status",         StringType),
      StructField("cd_education_status",       StringType),
      StructField("cd_purchase_estimate",      IntegerType),
      StructField("cd_credit_rating",          StringType),
      StructField("cd_dep_count",              IntegerType),
      StructField("cd_dep_employed_count",     IntegerType),
      StructField("cd_dep_college_count",      IntegerType)
    )),
    "date_dim" -> StructType(Seq(
      StructField("d_date_sk",                 IntegerType),
      StructField("d_date_id",                 StringType),
      StructField("d_date",                    DateType),
      StructField("d_month_seq",               IntegerType),
      StructField("d_week_seq",                IntegerType),
      StructField("d_quarter_seq",             IntegerType),
      StructField("d_year",                    IntegerType),
      StructField("d_dow",                     IntegerType),
      StructField("d_moy",                     IntegerType),
      StructField("d_dom",                     IntegerType),
      StructField("d_qoy",                     IntegerType),
      StructField("d_fy_year",                 IntegerType),
      StructField("d_fy_quarter_seq",          IntegerType),
      StructField("d_fy_week_seq",             IntegerType),
      StructField("d_day_name",                StringType),
      StructField("d_quarter_name",            StringType),
      StructField("d_holiday",                 StringType),
      StructField("d_weekend",                 StringType),
      StructField("d_following_holiday",       StringType),
      StructField("d_first_dom",               IntegerType),
      StructField("d_last_dom",                IntegerType),
      StructField("d_same_day_ly",             IntegerType),
      StructField("d_same_day_lq",             IntegerType),
      StructField("d_current_day",             StringType),
      StructField("d_current_week",            StringType),
      StructField("d_current_month",           StringType),
      StructField("d_current_quarter",         StringType),
      StructField("d_current_year",            StringType)
    )),
    "household_demographics" -> StructType(Seq(
      StructField("hd_demo_sk",                IntegerType),
      StructField("hd_income_band_sk",         IntegerType),
      StructField("hd_buy_potential",          StringType),
      StructField("hd_dep_count",              IntegerType),
      StructField("hd_vehicle_count",          IntegerType)
    )),
    "income_band" -> StructType(Seq(
      StructField("ib_income_band_sk",         IntegerType),
      StructField("ib_lower_bound",            IntegerType),
      StructField("ib_upper_bound",            IntegerType)
    )),
    "item" -> StructType(Seq(
      StructField("i_item_sk",                 IntegerType),
      StructField("i_item_id",                 StringType),
      StructField("i_rec_start_date",          DateType),
      StructField("i_rec_end_date",            DateType),
      StructField("i_item_desc",               StringType),
      StructField("i_current_price",           FloatType),
      StructField("i_wholesale_cost",          FloatType),
      StructField("i_brand_id",                IntegerType),
      StructField("i_brand",                   StringType),
      StructField("i_class_id",                IntegerType),
      StructField("i_class",                   StringType),
      StructField("i_category_id",             IntegerType),
      StructField("i_category",                StringType),
      StructField("i_manufact_id",             IntegerType),
      StructField("i_manufact",                StringType),
      StructField("i_size",                    StringType),
      StructField("i_formulation",             StringType),
      StructField("i_color",                   StringType),
      StructField("i_units",                   StringType),
      StructField("i_container",               StringType),
      StructField("i_manager_id",              IntegerType),
      StructField("i_product_name",            StringType)
    )),
    "promotion" -> StructType(Seq(
      StructField("p_promo_sk",                IntegerType),
      StructField("p_promo_id",                StringType),
      StructField("p_start_date_sk",           IntegerType),
      StructField("p_end_date_sk",             IntegerType),
      StructField("p_item_sk",                 IntegerType),
      StructField("p_cost",                    FloatType),
      StructField("p_response_target",         IntegerType),
      StructField("p_promo_name",              StringType),
      StructField("p_channel_dmail",           StringType),
      StructField("p_channel_email",           StringType),
      StructField("p_channel_catalog",         StringType),
      StructField("p_channel_tv",              StringType),
      StructField("p_channel_radio",           StringType),
      StructField("p_channel_press",           StringType),
      StructField("p_channel_event",           StringType),
      StructField("p_channel_demo",            StringType),
      StructField("p_channel_details",         StringType),
      StructField("p_purpose",                 StringType),
      StructField("p_discount_active",         StringType)
    )),
    "reason" -> StructType(Seq(
      StructField("r_reason_sk",               IntegerType),
      StructField("r_reason_id",               StringType),
      StructField("r_reason_desc",             StringType)
    )),
    "ship_mode" -> StructType(Seq(
      StructField("sm_ship_mode_sk",           IntegerType),
      StructField("sm_ship_mode_id",           StringType),
      StructField("sm_type",                   StringType),
      StructField("sm_code",                   StringType),
      StructField("sm_carrier",                StringType),
      StructField("sm_contract",               StringType)
    )),
    "store" -> StructType(Seq(
      StructField("s_store_sk",                IntegerType),
      StructField("s_store_id",                StringType),
      StructField("s_rec_start_date",          DateType),
      StructField("s_rec_end_date",            DateType),
      StructField("s_closed_date_sk",          IntegerType),
      StructField("s_store_name",              StringType),
      StructField("s_number_employees",        IntegerType),
      StructField("s_floor_space",             IntegerType),
      StructField("s_hours",                   StringType),
      StructField("s_manager",                 StringType),
      StructField("s_market_id",               IntegerType),
      StructField("s_geography_class",         StringType),
      StructField("s_market_desc",             StringType),
      StructField("s_market_manager",          StringType),
      StructField("s_division_id",             IntegerType),
      StructField("s_division_name",           StringType),
      StructField("s_company_id",              IntegerType),
      StructField("s_company_name",            StringType),
      StructField("s_street_number",           StringType),
      StructField("s_street_name",             StringType),
      StructField("s_street_type",             StringType),
      StructField("s_suite_number",            StringType),
      StructField("s_city",                    StringType),
      StructField("s_county",                  StringType),
      StructField("s_state",                   StringType),
      StructField("s_zip",                     StringType),
      StructField("s_country",                 StringType),
      StructField("s_gmt_offset",              FloatType),
      StructField("s_tax_precentage",          FloatType)
    )),
    "time_dim" -> StructType(Seq(
      StructField("t_time_sk",                 IntegerType),
      StructField("t_time_id",                 StringType),
      StructField("t_time",                    IntegerType),
      StructField("t_hour",                    IntegerType),
      StructField("t_minute",                  IntegerType),
      StructField("t_second",                  IntegerType),
      StructField("t_am_pm",                   StringType),
      StructField("t_shift",                   StringType),
      StructField("t_sub_shift",               StringType),
      StructField("t_meal_time",               StringType)
    )),
    "warehouse" -> StructType(Seq(
      StructField("w_warehouse_sk",           IntegerType),
      StructField("w_warehouse_id",           StringType),
      StructField("w_warehouse_name",         StringType),
      StructField("w_warehouse_sq_ft",        IntegerType),
      StructField("w_street_number",          StringType),
      StructField("w_street_name",            StringType),
      StructField("w_street_type",            StringType),
      StructField("w_suite_number",           StringType),
      StructField("w_city",                   StringType),
      StructField("w_county",                 StringType),
      StructField("w_state",                  StringType),
      StructField("w_zip",                    StringType),
      StructField("w_country",                StringType),
      StructField("w_gmt_offset",             FloatType)
    )),
    "web_page" -> StructType(Seq(
      StructField("wp_web_page_sk",           IntegerType),
      StructField("wp_web_page_id",           StringType),
      StructField("wp_rec_start_date",        DateType),
      StructField("wp_rec_end_date",          DateType),
      StructField("wp_creation_date_sk",      IntegerType),
      StructField("wp_access_date_sk",        IntegerType),
      StructField("wp_autogen_flag",          StringType),
      StructField("wp_customer_sk",           IntegerType),
      StructField("wp_url",                   StringType),
      StructField("wp_type",                  StringType),
      StructField("wp_char_count",            IntegerType),
      StructField("wp_link_count",            IntegerType),
      StructField("wp_image_count",           IntegerType),
      StructField("wp_max_ad_count",          IntegerType)
    )),
    "web_site" -> StructType(Seq(
      StructField("web_site_sk",              IntegerType),
      StructField("web_site_id",              StringType),
      StructField("web_rec_start_date",       DateType),
      StructField("web_rec_end_date",         DateType),
      StructField("web_name",                 StringType),
      StructField("web_open_date_sk",         IntegerType),
      StructField("web_close_date_sk",        IntegerType),
      StructField("web_class",                StringType),
      StructField("web_manager",              StringType),
      StructField("web_mkt_id",               IntegerType),
      StructField("web_mkt_class",            StringType),
      StructField("web_mkt_desc",             StringType),
      StructField("web_market_manager",       StringType),
      StructField("web_company_id",           IntegerType),
      StructField("web_company_name",         StringType),
      StructField("web_street_number",        StringType),
      StructField("web_street_name",          StringType),
      StructField("web_street_type",          StringType),
      StructField("web_suite_number",         StringType),
      StructField("web_city",                 StringType),
      StructField("web_county",               StringType),
      StructField("web_state",                StringType),
      StructField("web_zip",                  StringType),
      StructField("web_country",              StringType),
      StructField("web_gmt_offset",           FloatType),
      StructField("web_tax_percentage",       FloatType)
    ))
  )

    def createAll(inputPath: String)(implicit spark: SparkSession): Unit = {
        tableName.foreach(read(inputPath))
    }

    def read(path: String)(tableName: String)(implicit spark: SparkSession): Unit = {
        val rdd = spark.read.parquet(s"$path/$tableName").rdd
        val schema = schemas(tableName)
        spark.createDataFrame(rdd, schema).createOrReplaceTempView(tableName)
    }
}
