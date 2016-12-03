package de.wiasberlin.snp500

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

import de.wiasberlin.snp500.util.{GetSymbols, SaveStrings}

import collection.JavaConverters._
import yahoofinance.YahooFinance
import yahoofinance.histquotes.{HistoricalQuote, Interval}

/**
  * Created by valerij on 12/2/16.
  */
object AmazonCrawler extends App {
  private val pathToSymbols = "resourses/constituents.txt"

  private def roundTimeToDay(milliseconds : Long) = {
    val millisecondsInDay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
    (milliseconds / millisecondsInDay) * millisecondsInDay
  }

  private def historicalQuoteToString(quote : HistoricalQuote): String = {
    s"${roundTimeToDay(quote.getDate.getTimeInMillis)}, ${quote.getSymbol}, ${quote.getOpen}, ${quote.getHigh}, ${quote.getLow}, ${quote.getClose}, ${quote.getVolume}"
  }

  val from = Calendar.getInstance()
  val to = Calendar.getInstance()
  from.add(Calendar.YEAR, -5)

  val symbols = GetSymbols(pathToSymbols).toArray

  val symbol2Stock = YahooFinance.get(symbols, from, to, Interval.DAILY).asScala
  val dailies = symbol2Stock.toSeq.flatMap(_._2.getHistory.asScala).map(historicalQuoteToString)

  val path = "/home/valerij/crawled"
  SaveStrings(path, dailies)
}
