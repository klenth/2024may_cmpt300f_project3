import java.time.LocalDate

/** A single stock record, including the opening, high, low, closing, and adjusted closing share prices and volume
 of a stock on an exchange on a day */
case class StockRecord(date: LocalDate, ticker: String, exchange: String,
                       open: Double, high: Double, low: Double, close: Double, closeAdj: Double, volume: Long)
