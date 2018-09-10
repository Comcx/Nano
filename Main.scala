
import scala.util._
import java.util.Date
import scala.io.StdIn._
import scala.io.Source

import tool.Tool
import Tool.benchmark

import ender._

object Main {

  def main (args: Array [String]): Unit = { Tool.showVersion ("Comcx")
/*
    if (args.length < 2) {
      println ("Not enough parameters!")
      return
    }*/
    val t_start = System.nanoTime ()
/*
    val endor = new Ender ("end",
                           if (args.length >= 3) args(2).toInt else 4)

    val ans = endor.process (args (0))
    endor save ans to args (1)
    println (args (0) + " -> " + args (1))
 */
    /*
    Tool.criterion { List (
      benchmark ("test", () => println ("hello, ender!"), true)
     )}*/


    //import actuator._
    //import reader._

    val a = new parser.Parser()
    val r = new scanner.Reader()

    if (args.length < 1) {println("Not enough parameters!"); return}
    val raw = Source.fromFile (args(0)).getLines.toList.foldLeft("")(_ + _ + "\n").trim
      //.filter(c => c != '\n')

    //println((raw))
    //println("====================")
    //println()
    val ans = a.parse(r.read(raw))
    println(ans)


    val t_end = System.nanoTime ()
    println (Tool.hrLine ())
    println ("consumed time: %d ns".format (t_end - t_start))
  }

}







