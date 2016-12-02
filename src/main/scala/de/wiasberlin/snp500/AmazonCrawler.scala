package de.wiasberlin.snp500

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import de.wiasberlin.snp500.util.SaveStrings

import collection.JavaConverters._
import yahoofinance.YahooFinance
import yahoofinance.histquotes.{HistoricalQuote, Interval}

/**
  * Created by valerij on 12/2/16.
  */
object AmazonCrawler extends App {
  private val pathToSymbols = "resourses/constituents.txt"

  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  private def getSymbols() = {
    io.Source.fromFile(new File(pathToSymbols)).getLines().toArray
  }

  private def historicalQuoteToString(quote : HistoricalQuote): String = {
    s"${dateFormat.format(quote.getDate.getTime)}, ${quote.getSymbol}, ${quote.getOpen}, ${quote.getHigh}, ${quote.getLow}, ${quote.getClose}, ${quote.getVolume}"
  }

  val from = Calendar.getInstance()
  val to = Calendar.getInstance()
  from.add(Calendar.YEAR, -5)

  val symbols = getSymbols()

  val symbol2Stock = YahooFinance.get(symbols, from, to, Interval.DAILY).asScala
  val dailies = symbol2Stock.toSeq.flatMap(_._2.getHistory.asScala).map(historicalQuoteToString)

  val path = "/home/valerij/crawled"
  SaveStrings(path, dailies)
}
