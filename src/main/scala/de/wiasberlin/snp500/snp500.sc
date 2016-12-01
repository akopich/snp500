import java.io.{BufferedWriter, File, FileWriter}


// pre-processes the data from http://pages.swcp.com/stocks/

class Datum(val date: Int, val company : String, val open : Double, val close: Double) {
  def getLogReturnRate() = math.log(1+ (close - open) / open)

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

val file = new File("/home/valerij/sp500.csv")
val bw = new BufferedWriter(new FileWriter(file))

val csvLines = data.filter(datum => goodCompanies contains datum.company)
                    .groupBy(_.date)
                    .values
                    .map(_.sortBy(_.company).map(_.getLogReturnRate()))
                    .map(_.mkString(","))

csvLines.foreach(s => {
    bw.write(s)
    bw.newLine()
  })

bw.close()

