package de.wiasberlin.snp500.util

import java.io.{BufferedWriter, File, FileWriter}

/**
  * Created by valerij on 12/2/16.
  */
object SaveStrings {
  def apply(path : String, strings : Iterable[String]) : Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))

    for (s <- strings) {
      bw.write(s)
      bw.newLine()
    }

    bw.close()
  }
}
