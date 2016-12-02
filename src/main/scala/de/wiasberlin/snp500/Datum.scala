package de.wiasberlin.snp500

/**
  * Created by valerij on 12/2/16.
  */
class Datum(val date: String, val company : String, val open : Double, val close: Double) {
  def getLogReturnRate() = math.log(1+ (close - open) / open)

  override def toString = s"Datum($date, $company, $open, $close)"
}

object Datum {
  def apply(s : String) = {
    val split = s.split(",")
    new Datum(split(0), split(1), split(2).toDouble, split(5).toDouble)
  }

  def applyToTickData(company : String)(s : String) = {
    val split = s.split(",")
    new Datum(split(0), company, split(4).toDouble, split(1).toDouble)
  }

}