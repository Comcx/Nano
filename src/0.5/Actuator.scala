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
    var env = p.env0
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
        val list = r.read(input)
        env = list match {
          case L(L(Op("def"), Var(s)), e) => (s, p.run(e, env)) :: env
          case _ => env
        }
        val res = list match {
          case L(L(Op("def"), Var(s)), e) => "defined " + s
          case _ => p.parse(list, env)
        }
        println("-> " + res)
      }
    }// end while
  }


}









