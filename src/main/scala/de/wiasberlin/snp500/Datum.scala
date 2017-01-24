package de.wiasberlin.snp500

/**
  * Created by valerij on 12/2/16.
  */
class Datum(val date: Long, val company : String, val open : Double, val close: Double) {
  override def toString = s"Datum($date, $company, $open, $close)"
}

object Datum {
  def apply(s : String): Datum = {
    val Array(dateStr, company, openStr, _, _, closeStr) = s.split(",")
    new Datum(dateStr.toLong, company, openStr.toDouble, closeStr.toDouble)
  }

}

class DatumWithStringDate(val date: String, val company : String, val open : Double, val close: Double) {
  def setDate(newDate : Long) = new Datum(newDate, company, open, close)
}

object DatumWithStringDate {
  def apply(company : String)(s : String): DatumWithStringDate = {
    val Array(dateStr, closeStr, _, _, openStr) = s.split(",")
    new DatumWithStringDate(dateStr, company, openStr.toDouble, closeStr.toDouble)
  }
}

