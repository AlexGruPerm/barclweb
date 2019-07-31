package models

import com.datastax.oss.driver.api.core.cql.Row

object CommRowConverters {

  val rowToTicker :(Row => Ticker) = (row: Row) =>
    Ticker(
      row.getInt("ticker_id"),
      row.getString("ticker_code"),
      row.getString("ticker_first"),
      row.getString("ticker_seconds")
    )

  val rowToBarCalcProperty :(Row => BarCalcProperty) = (row :Row) =>
    BarCalcProperty(
      row.getInt("ticker_id"),
      row.getInt("bar_width_sec"),
      row.getInt("is_enabled")
    )

}
