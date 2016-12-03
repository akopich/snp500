package de.wiasberlin.snp500

/**
  * Created by valerij on 12/3/16.
  */
object LogReturnRate {
  def apply(earlierAndLater : (Datum, Datum)) : Double = earlierAndLater match {
    case (earlier, later) => math.log(later.close / earlier.close)
  }
}
