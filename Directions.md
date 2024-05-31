# Directions

The goal of this small project is to read a relatively large data file of company financial data (stock price and volume) and print out a summary of it. You will use lazy evaluation so that only necessary parts of the file are read, saving a significant amount of time.

## Prerequisites
Make sure you download `nasdaq.zip` (linked in the assignment on Canvas) and place it in the `data` directory of your project. At about 163 MiB (706 MiB uncompressed) it is too large to go into a GitHub repository, so it must be downloaded separately. However, **do not extract this zip file:** the program will read the data directly from the zip.

## Step 1
Take a look at `data/nasdaq_2021 sample.csv`; this is a small sample of the data in `nasdaq_2021.csv` (inside `data/nasdaq_symbols.zip`), which includes the ticker symbol (such as AAPL), the name (such as Apple Inc. Common Stock), and the country of various companies listed on the NASDAQ stock exchange. The `Company` case class already defined is meant to hold this sort of data.

The `ZipEntryReader` class (provided) is used for reading text files line-by-line inside a zip file. When you create one, you need to pass the name of the zip file (such as `data/nasdaq_symbols.zip`) and the name of the file within the zip that you wish to read (such as `nasdaq_2021.csv`). You can then call its `readLine()` method to read one line of the file at a time, and use `close()` to close it when done.

In `Nasdaq.scala`, add some code at the top of `nasdaqMain()` to make a `ZipEntryReader` to read `nasdaq_2021.csv` (inside `data/nasdaq_symbols.zip`). Call `readLine()` a few times and print out the result to show that it is working, and make sure to `close()` it at the end of `nasdaqMain()`.

## Step 2
It would be easy to read the entirety of `nasdaq_2021.csv` by calling `readLine()` until there are no more lines — and that would probably be fine, because it's not that large a file (fewer than 8000 lines). However, to prepare us for the much larger NASDAQ data file, we will read the file lazily: lines are read from it only as needed.

To do this, fill in the body of `zipEntryLines()`, which takes a `ZipEntryReader` and returns a `LazyList[String]` of the lines in the file. Follow the class example of building a lazy list: read a single line from the file and recursively call `zipEntryLines`, using the lazy list construction operator `#::`. (When there are no more lines in the file, just return an empty `LazyList`.)

In your `nasdaqMain()`, test that your `zipEntryLines()` is working by calling it and printing out all the lines.

## Step 3
Fill in the `lineToCompany()` function (you can use the Pokémon class example as inspiration). Keep in mind that some lines are missing a country, so your code should handle both "ticker,name,country" and "ticker,name" lines.

Back in `nasdaqMain()`, `map()` the lines using your `lineToCompany()` to get a `LazyList[Company]` and print all of them out to test your code.

## Step 4
In `nasdaqMain()`, add code inside the loop so that the user can enter a symbol (such as `AAPL`) and the program will print the name and country of the country with that ticker symbol. You can do this by `filter()`ing your `LazyList[Company]`, or you can build a `Map[String, Company]` similar to what we did in the Pokémon class example.

## Step 5
The other data file is `NASDAQ 1962-2024.csv` (inside `nasdaq.zip`), which contains daily stock information on the NASDAQ stock exchange for approximately the last 60 years (there is a sample in the `data` directory). This file is much (much) larger than the company data file, so lazy evaluation will be quite important when reading this file! The `StockRecord` case class (already written for you) holds data 

Repeat Steps 1 and 3 for daily stock information file and data, adding a new `ZipEntryReader` to `nasdaqMain()` and filling in `lineToStockRecord()`, then test your code. (Remember that you can use <code>take(*n*)</code> on a `LazyList` to grab only the first <code>*n*</code> values — this should be very fast for small numbers since the file is read lazily.)

## Step 6
Add code inside the loop in `nasdaqMain()` to print out a summary of stock data by year for the ticker symbol the user enters. You should print out a table showing year, average closing price, and average volume for each year of that stock. For example, it might look something like this (these are the correct numbers, so you can test your code against this):

<pre>Ticker symbol: <span style="color: #008; font-weight: 900;">aapl</span>
[AAPL] Apple Inc. Common Stock (United States) records:
    Year    Average close      Average volume
    1980    $    0.10             103,450,092
    1981    $    0.08              32,398,988
    1982    $    0.07              84,446,671
    1983    $    0.13             175,940,755
    1984    $    0.09             165,925,033
    1985    $    0.07             180,524,888
    1986    $    0.11             210,763,724
    1987    $    0.27             236,250,232
    1988    $    0.29             163,213,350
    1989    $    0.29             202,007,244
    1990    $    0.27             175,501,742
    1991    $    0.38             226,670,558
    1992    $    0.40             161,960,289
    1993    $    0.30             223,134,102
    1994    $    0.25             226,809,111
    1995    $    0.30             294,708,488
    1996    $    0.19             209,426,066
    1997    $    0.14             284,440,158
    1998    $    0.23             457,119,822
    1999    $    0.44             544,058,355
    2000    $    0.69             477,387,288
    2001    $    0.31             381,684,664
    2002    $    0.29             305,610,822
    2003    $    0.28             282,659,733
    2004    $    0.54             483,339,955
    2005    $    1.41             723,813,422
    2006    $    2.14             859,358,430
    2007    $    3.87             984,047,751
    2008    $    4.29           1,130,360,498
    2009    $    4.43             568,467,011
    2010    $    7.85             599,305,266
    2011    $   10.99             492,298,966
    2012    $   17.44             527,856,817
    2013    $   14.59             406,434,800
    2014    $   20.42             252,610,922
    2015    $   27.03             207,397,617
    2016    $   24.04             153,690,123
    2017    $   35.25             108,538,270
    2018    $   44.94             136,080,258
    2019    $   50.29             112,122,788
    2020    $   93.15             157,564,646
    2021    $  138.66              90,524,627
    2022    $  153.12              87,910,376
    2023    $  171.67              59,217,028
    2024    $  179.32              61,071,337</pre>

Remember that you can use [f-strings](https://docs.scala-lang.org/scala3/book/string-interpolation.html#the-f-interpolator-f-strings) to format values nicely in Scala with `printf`-style formatting codes. The average closing price and average volume columns shown in the example above use `%8.2f` and `%,20d` respectively. (To show a `$` character in a formatted string, double it: `f"... $$ ..."`.)

Some hints for doing this:
- This can be done without writing a loop of any kind (although it is not a requirement of the project to do that). Try to figure out how to combine the pieces that you know like `map()` and `foreach()` to make that happen!
- You can use `filter()` to grab only the records of the relevant stock, but that's a bad idea in this instance: it will need to check every row in the file since it doesn't know that all the records for the same stock are consecutive. Instead, combine `dropWhile()` and `takeWhile()`, both of which take predicates (`StockRecord => Boolean`), to first skip past all the records before the relevant stock (`dropWhile()`), and then only take the rows while they belong to the relevant stock (`takeWhile()`). **If you do this correctly, stocks listed early in the file (such as AAPL and AMZN) should run very very fast, while ones later in the file (like GOOG and MSFT) should take much longer.
- Use the `groupBy()` method to group the rows by year. The result is a `Map[Int, LazyList[StockRecord]]`, which allows you to easily compute the averages within each year.
- An easy way to sort the resulting map by year is to put it in a `SortedMap`, using `SortedMap.from(...)`.
- If you have a list of a numeric type (such as `Double` or `Long`), you can use `.sum()` to calculate its total. This is helpful when computing averages.
