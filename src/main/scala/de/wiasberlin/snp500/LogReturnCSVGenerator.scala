package de.wiasberlin.snp500

import java.io.File

import de.wiasberlin.snp500.util.SaveStrings

/**
  * Created by valerij on 12/3/16.
  *
  *
  * pre-processes the data from http://pages.swcp.com/stocks/
  */
object LogReturnCSVGenerator extends App {
  def datums2logReturnCSV(data : Seq[Datum]): Seq[String] = {
    val goodCompanies = data.groupBy(_.date).map(_._2.map(_.company).toSet).reduce(_ intersect _)

    val datums = data.filter(goodCompanies contains _.company)
      .groupBy(_.date).toSeq
      .sortBy(_._1)
      .unzip._2
      .map(_.sortBy(_.company))

    datums.sliding(2)
              .map({case(Seq(first, second)) => (first zip second) map LogReturnRate.apply})
              .map(_.mkString(",")).toSeq
  }

  val data = io.Source.fromFile(new File("/home/valerij/crawled100"))
    .getLines
    .map(Datum.apply).toVector

  val csvLines = datums2logReturnCSV(data)

  val path = "/home/valerij/crawledsp100.csv"

  SaveStrings(path, csvLines)
}
