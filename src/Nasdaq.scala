import java.time.LocalDate
import java.util.Scanner
import scala.collection.SortedMap

def zipEntryLines(reader: ZipEntryReader): LazyList[String] =
  reader.readLine() match
    case None => LazyList()
    case Some(line) => line #:: zipEntryLines(reader)

//def zipEntryLines(reader: ZipEntryReader): List[String] =
//  val entryBuffer = ArrayBuffer[String]()
//  var cont = true
//  while cont do
//    val maybeLine = reader.readLine()
//    if maybeLine.isEmpty then
//      cont = false
//    else
//      entryBuffer += maybeLine.get
//  entryBuffer.toList

def lineToStockRecord(line: String): StockRecord =
  line.split(",").toList match
    case date :: ticker :: exchange :: open :: high :: low :: close :: adjClose :: volume :: Nil =>
      StockRecord(LocalDate.parse(date), ticker, exchange,
        open.toDouble, high.toDouble, low.toDouble, close.toDouble, adjClose.toDouble, volume.toLong
      )
    case _ => throw new RuntimeException(s"Invalid line: '$line'")

def lineToCompany(line: String): Company =
  line.split(",").toList.map(_.trim) match
    case ticker :: name :: Nil =>
      Company(ticker, name, "")
    case ticker :: name :: country :: Nil =>
      Company(ticker, name, country)
    case _ => throw new RuntimeException(s"Invalid line: '$line'")

@main
def nasdaqMain(): Unit =
  val symbolReader = ZipEntryReader("data/nasdaq_symbols.zip", "nasdaq_2021.csv")
  val symbols = zipEntryLines(symbolReader).drop(1).map(lineToCompany)

  val reader = ZipEntryReader("data/nasdaq.zip", "NASDAQ 1962-2024.csv")
  val records = zipEntryLines(reader).drop(1).map(lineToStockRecord)
  //println(s"All records: ${records.count(r => true)}")
  //println(records.dropWhile(_.ticker != "AAPL").takeWhile(_.ticker == "AAPL").count(r => true))
  //println(s"MSFT records: ${records.count(_.ticker == "MSFT")}")

  val time = System.currentTimeMillis()
  val tickerMap = symbols.foldLeft[Map[String, Company]](Map())((m, c) => m.updated(c.ticker, c))

  val in = Scanner(System.in)
  while (true) do
    print("Ticker symbol: ")
    val ticker = in.nextLine().toUpperCase

    tickerMap.get(ticker) match
      case Some(company) => println(s"[${company.ticker}] ${company.name} (${company.country}) records:")
      case None => println(s"No company with ticker symbol ${ticker} is known.")

    println("\tYear    Average close      Average volume")

    val s = SortedMap.from(records.dropWhile(_.ticker != ticker).takeWhile(_.ticker == ticker).groupBy(_.date.getYear))
      .map((year, r) => (year, r.map(_.closeAdj).sum() / r.length, r.map(_.volume).sum() / r.length))
    s.foreach((year, avgClose, avgVolume) => println(f"\t$year    $$$avgClose%8.2f    ${avgVolume.toLong}%,20d"))

  reader.close()
  symbolReader.close()