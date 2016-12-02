package de.wiasberlin.snp500

import de.wiasberlin.snp500.util.{GetSymbols, SaveStrings}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by valerij on 12/2/16.
  */
object DownloadTickData extends App {
  private def fixGoogleTime(datums : Seq[Datum]) = {
    datums.foldLeft(Vector[Datum]())((res, datum) => {
      val isNewDay = datum.date.startsWith("a")
      val day = (if (res.isEmpty) 0 else res.last.date.split("M").head.toInt) + (if (isNewDay) 1 else 0)
      val minute = if (isNewDay) 0 else datum.date.toInt

      res :+ datum.setDate(s"${day}M$minute")
    })
  }

  private def html2Datums(symbol2html : (String, Source)) = symbol2html match {
    case(symbol, html) => {
      val rawTime = html.getLines().toVector.drop(7).map(Datum.applyToTickData(symbol))
      fixGoogleTime(rawTime)
    }
  }

  private def createRequestYahoo(symbol : String) = s"http://chartapi.finance.yahoo.com/instrument/1.0/$symbol/chartdata;type=quote;range=150d/csv"

  private def createRequestGoogle(symbol : String) = s"https://www.google.com/finance/getprices?i=60&p=10d&f=d,o,h,l,c,v&df=cpct&q=$symbol"

  private val pathToSymbols = "resourses/constituents.txt"

  val symbols = GetSymbols(pathToSymbols)

  val datums = (symbols zip symbols.map(createRequestGoogle).map(io.Source.fromURL)).flatMap(html2Datums)

//  datums.groupBy(_.date).map()

  val path = "/home/valerij/tickData"

  val times = datums.map(_.date).distinct.sorted
  val datumtime = datums.map(datum => (datum.date, datum.company)).toSet

  val lines = symbols.map(symbol => times.map(time => datumtime contains (time, symbol) )
                                          .map(b => if (b) 1 else 0)
                                          .mkString(","))

  SaveStrings(path, lines)

//  SaveStrings("", )

  val a =1
}
