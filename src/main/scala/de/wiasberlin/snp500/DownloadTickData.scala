package de.wiasberlin.snp500

import de.wiasberlin.snp500.util.{GetSymbols, SaveStrings}

import scala.io.Source

/**
  * Created by valerij on 12/2/16.
  */
object DownloadTickData extends App {
  def getClosest(time0 : Seq[Long], time : Seq[Long]) = {
    time0.map(t => {
      val diff = time.map(_ - t).map(math.abs)
      val min = diff.min
      diff.indexWhere(_ == min)
    })
  }

  private def html2Datums(symbol2html : (String, Source)) = symbol2html match {
    case(symbol, html) => {
      val rawTime = html.getLines().toVector.drop(7).map(Datum.applyToTickData(symbol))
      rawTime.
    }
  }

  private def createRequestYahoo(symbol : String) = s"http://chartapi.finance.yahoo.com/instrument/1.0/$symbol/chartdata;type=quote;range=150d/csv"

  private def createRequestGoogle(symbol : String) = s"https://www.google.com/finance/getprices?i=60&p=10d&f=d,o,h,l,c,v&df=cpct&q=$symbol"

  private val pathToSymbols = "resourses/constituents.txt"

  val symbols = GetSymbols(pathToSymbols).take(10)

  val datums = (symbols zip symbols.map(createRequestGoogle).map(io.Source.fromURL)).flatMap(html2Datums)

  val path = "/home/valerij/tickData"

  val grouped = (datums.groupBy(_.company).map(_._2.length).toSeq)

//  val times = symbols.map(symbol => datums.filter(_.company == symbol).map(_.date.toLong))
//  val indx = times.drop(1).map(time => getClosest(times(0), time))

//  println(indx.forall(_ == times(0).indices))
//
  val a =1
}
