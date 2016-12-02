import java.io.{BufferedWriter, File, FileWriter}

import de.wiasberlin.snp500.Datum
import de.wiasberlin.snp500.util.SaveStrings


// pre-processes the data from http://pages.swcp.com/stocks/


def datums2logReturnCSV(data : Seq[Datum]) = {
  val goodCompanies = data.groupBy(_.date).map(_._2.map(_.company).toSet).reduce(_ intersect _)

  data.filter(datum => goodCompanies contains datum.company)
    .groupBy(_.date)
    .values
    .map(_.sortBy(_.company).map(_.getLogReturnRate()))
    .map(_.mkString(","))
}

val data = io.Source.fromFile(new File("/home/valerij/crawled"))
  .getLines
  .map(Datum.apply).toVector


val csvLines = datums2logReturnCSV(data)

val path = "/home/valerij/crawledsp500.csv"

SaveStrings(path, csvLines)
