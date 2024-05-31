import java.time.LocalDate
import java.util.Scanner
import scala.collection.SortedMap

/** Returns a LazyList of lines from a ZipEntryReader */
// Your code should call reader.readLine() to get an Option[String] of the next line of the file (None if there are none
// remaining) lazily adding (with #::) the remaining lines of the file.
def zipEntryLines(reader: ZipEntryReader): LazyList[String] =
  ??? // replace with your code!

/** Converts a CSV line string to a StockRecord object */
// Use the Pokémon example from class for inspiration. Use LocalDate.parse() to parse the date string.
def lineToStockRecord(line: String): StockRecord =
  ??? // replace with your code!

/** Converts a CSV line string to a Company object */
// Similar to lineToStockRecord(). However, some lines of the CSV don't include a company name, so your code should
// work with both (ticker,name,country) lines and (ticker,name) ones. (Use "" for country if not specified.)
def lineToCompany(line: String): Company =
  ??? // replace with your code!

@main
def nasdaqMain(): Unit =
  // Create ZipEntryReader objects to read nasdaq_2021.csv from data/nasdaq_symbols.zip and
  // "NASDAQ 1962-2024.csv" from data/nasdaq.zip
  // (If you haven't already, download nasdaq.zip and put in the data directory!)
  // Also add .close() calls for both at the bottom of this function!



  // Use your zipEntryLines() method to read LazyLists of lines from both files, then .map() them to Company/StockRecord
  // objects using the methods you wrote above



  val in = Scanner(System.in)
  while (true) do
    print("Ticker symbol: ")
    val ticker = in.nextLine().toUpperCase

    // Add code:
    //   - Check whether the ticker symbol the user entered is listed in the nasdaq_symbols file, and if it is, print
    //      the name and country of the company (you can either use your LazyList of companies directly or dump them
    //      into a Map first).
    //      (Not all valid ticker symbols are listed in nasdaq_symbols.zip; if this one isn't, then just proceed with
    //      the next step below.)
    //   - In your LazyList of StockRecords, use .dropWhile() and .takeWhile() (see directions) to filter to the
    //      user's entered ticker symbol. Use .group


  // Close both the ZipEntryReaders you created at the top