package de.wiasberlin.snp500.util

import java.io.File

/**
  * Created by valerij on 12/2/16.
  */
object GetSymbols {
  def apply(pathToSymbols : String): Vector[String] = {
    io.Source.fromFile(new File(pathToSymbols)).getLines().toVector
  }
}
