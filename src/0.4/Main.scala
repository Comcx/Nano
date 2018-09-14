
import scala.util._
import java.util.Date
import scala.io.StdIn._
import scala.io.Source

import tool.Tool
//import Tool.benchmark

//import ender._

object Main {

  def main (args: Array [String]): Unit = { //Tool.showVersion ("Comcx")

    def showNanoLogo(): Unit = println(
         "   _____  ___   ____   _______\n" +
         "  / _  | / _ | / _  | / __   /\n" +
         " / / | |/ __ |/ / | |/ /_ / / \n" +
         "/ /  | /_/ |_/ /  | |\\ ____/       by Comcx \n"
    )
    showNanoLogo()


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


    val a = new actuator.Actuator()
    val p = new parser.Parser()
    val r = new scanner.Reader()

    if (args.length == 2) {
      if (args(0) == "-l") {
        val raw = Source.fromFile (args(1)).getLines.toList
          .foldLeft("")(_ + _ + "\n").trim
        val ans = p.parse(r.read(raw))
        println(ans)
      }
      else {println("Wrong format of input!"); return}
    }
    else {
      a.repl()
    }

    val t_end = System.nanoTime ()
    println("--------------------------------------------------")
    //println (Tool.hrLine ())
    println ("consumed time: %d ns".format (t_end - t_start))
  }

}







