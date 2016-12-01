import java.io.File

class Datum(val date: Int, val company : String, val open : Double, val close: Double) {

  override def toString = s"Datum($date, $company, $open, $close)"
}

object Datum {
  def apply(s : String) = {
    val split = s.split(",")
    new Datum(split(0).toInt, split(1), split(2).toDouble, split(5).toDouble)
  }
}

val data = io.Source.fromFile(new File("/home/valerij/sp500hst.txt"))
  .getLines
  .map(Datum.apply).toVector

val goodCompanies = data.groupBy(_.date).map(_._2.map(_.company).toSet).reduce(_ intersect _)

data.filter(datum => goodCompanies contains datum.company).length / 379




