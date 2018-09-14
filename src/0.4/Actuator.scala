package actuator

import java.util.Scanner
import parser._
import scanner._


class Actuator() {

  def repl(): Unit = {

    val p = new Parser()
    val r = new Reader()
    val s = new Scanner(System.in)

    var go_on = true
    while (go_on) {

      print("\nnano> ")
      val input = Console.readLine()
      if (input == ":q") go_on = false
      else if (input == ":load") ???
      else if (input == ":?") println(
        "Wanna help?\n" +
        "[commands]: (:q quit) (:? help)\n" +
        "..."
      )
      else {
        val res = p.parse(r.read(input))
        println("-> " + res)
      }
    }// end while
  }


}









