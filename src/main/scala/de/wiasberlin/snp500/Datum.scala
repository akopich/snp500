package de.wiasberlin.snp500

/**
  * Created by valerij on 12/2/16.
  */
class Datum(val date: Long, val company : String, val open : Double, val close: Double) {
  override def toString = s"Datum($date, $company, $open, $close)"
}

object Datum {
  def apply(s : String) = {
    val split = s.split(",")
    new Datum(split(0).toLong, split(1), split(2).toDouble, split(5).toDouble)
  }

}

class DatumWithStringDate(val date: String, val company : String, val open : Double, val close: Double) {
  def setDate(newDate : Long) = new Datum(newDate, company, open, close)
}

object DatumWithStringDate {
  def apply(company : String)(s : String) = {
    val split = s.split(",")
    new DatumWithStringDate(split(0), company, split(4).toDouble, split(1).toDouble)
  }
}

