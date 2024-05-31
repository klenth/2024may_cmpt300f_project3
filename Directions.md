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

