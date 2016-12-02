import java.io.{BufferedWriter, File, FileWriter}

import de.wiasberlin.snp500.util.SaveStrings


// pre-processes the data from http://pages.swcp.com/stocks/

class Datum(val date: String, val company : String, val open : Double, val close: Double) {
  def getLogReturnRate() = math.log(1+ (close - open) / open)

  override def toString = s"Datum($date, $company, $open, $close)"
}

object Datum {
  def apply(s : String) = {
    val split = s.split(",")
    new Datum(split(0), split(1), split(2).toDouble, split(5).toDouble)
  }
}

val data = io.Source.fromFile(new File("/home/valerij/crawled"))
  .getLines
  .map(Datum.apply).toVector

val goodCompanies = data.groupBy(_.date).map(_._2.map(_.company).toSet).reduce(_ intersect _)

val path = "/home/valerij/crawledsp500.csv"

val csvLines = data.filter(datum => goodCompanies contains datum.company)
                    .groupBy(_.date)
                    .values
                    .map(_.sortBy(_.company).map(_.getLogReturnRate()))
                    .map(_.mkString(","))

SaveStrings(path, csvLines)
