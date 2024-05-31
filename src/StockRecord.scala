import java.time.LocalDate

case class StockRecord(date: LocalDate, ticker: String, exchange: String,
                       open: Double, high: Double, low: Double, close: Double, closeAdj: Double, volume: Long)
