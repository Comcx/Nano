package actuator

import java.util.Scanner
import scala.io.Source
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
      else if (input.split(" ").head == ":na") {
        val file = input.split(" ").tail.head
        val lines = Source.fromFile(file).getLines.toList
        lines.foreach(l =>
          env = r.read(l) match {
            case L(L(Op("def"), Var(s)), e) => (s, p.run(e, env)) :: env
            case _ => env
          }
        )
        println("-> loaded " + file)
      }
      else if (input.split(" ").head == ":no") {
        val file = input.split(" ").tail.head
        val raw = Source.fromFile(file).getLines.toList
          .foldLeft("")(_ + _ + "\n").trim
        val res = p.parse(r.read(raw), env)
        println("-> " + res)
      }
      else if (input == ":?") println(
        "Wanna help?\n" +
        "[commands]: (\n" +
        "  (:q quit)\n" +
        "  (:? help)\n" +
        "  (:na <file>)\n" +
        "  (:no <file>)\n" +
        "  ... )"
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









