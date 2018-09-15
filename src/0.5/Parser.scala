package parser



import scala.util._
import scanner.Reader
import scala.io.Source


trait SExp
case object Nothing extends SExp
case class Error(msg: String, e: SExp) extends SExp
case class Number(i: Int) extends SExp
case class Var(s: String) extends SExp
case class Quote(s: SExp) extends SExp
case class Str(s: String) extends SExp
case class Op(op: String) extends SExp
case class L(op: SExp, x: SExp*) extends SExp
case class ChurchNumeral(i: Int, f: SExp) extends SExp


class Parser {

  type Bind = (String, SExp)

  val r = new Reader
  def env0: List [Bind] = Nil
  def env_+ (s: String, v: SExp, env: List [Bind]): List [Bind] =
    (s, v) :: env
  //def env_::(env1: List[Bind], env2: List[Bind]): List[Bind] =

  private def lookup (name: String, env: List [Bind]): Option [SExp] =
    env match {
      case (x :: xs)  => if (x._1 == name) Some (x._2) else lookup (name, xs)
      case _ => None
    }

  case class Closure (x: Var, e: SExp, env: List [Bind]) extends SExp


  def run (e: SExp, env: List [Bind]): SExp = e match {

    case Number(i) => Number(i)
    case Quote(e) => e
    case Closure(v, e, en) => Closure(v, e, en)
    case Var("") => Nothing
    case Var(s) => lookup (s, env) match {
      case Some (x) => x
      case None  => Error("No such variable!", Var(s))
    }

    /**
      * Bindings
      */
    case L( L(Op (":="), L(v, e)), exp) => v match {
      case Var(s) => run(exp, env_+(s, run(e, env), env))
      case _ => run(v, env) match { // is SExp?
        case Var(s) => run(exp, env_+(s, run(e, env), env))
        case _ => Error("Bind to nothing?", run(v, env))
      }
    }

    /**
      *  Conditions
      */
    case L(L(Op("if"), e), L(e0, e1)) => run(e, env) match {

      case Var("true") => run(e0, env)
      case Var("false") => run(e1, env)
      case _ => Error("Wrong if function!", L(L(Op("if"), run(e, env)), L(e0, e1)))
    }


    case L(Op("`"), exp) => run(run(exp, env), env)
    case L(Op("@"), Var(url)) => {

      val raw = Source.fromFile(url).getLines.toList
        .foldLeft("")(_ + _ + "\n").trim
      r.read(raw)
    }


    case L(L(Op("+"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m + n)
      case _ => Error("", L(Str("+?"), L(run(x, env), run(y, env))))
    }
    case L(L(Op("-"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m - n)
      case _ => Error("", L(Str("-?"), L(run(x, env), run(y, env))))
    }
    case L(L(Op("*"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m * n)
      case _ => Error("", L(Str("*?"), L(run(x, env), run(y, env))))
    }
    case L(L(Op("/"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m / n)
      case _ => Error("", L(Str("/?"), L(run(x, env), run(y, env))))
    }
    case L(L(Op("="), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => if (m == n) Var("true") else Var("false")
      case (Var(a), Var(b)) => if (a == b) Var("true") else Var("false")
      case _ => Var("false")
    }
    case L(Op("'"), e) => e
    case L(L(Op(":"), v), e) => v match {
      case Var(s) => Closure(Var(s), e, env)
      case _ => run(v, env) match {
        case Var(s) => Closure(Var(s), e, env)
        case _ => Error("Lambda has no parameter!", run(v, env))
      }
    }
    case L(Op("print"), e) => {val res = run(e, env); print(pretty(res)); Nothing}
    case L(L(Op("::"), a), b) => L(run(a, env), run(b, env))
    case L(Op("0:"), z) => run(z, env) match {
      case L(a, b) => a
      case _ => Error("Wrong usage of 0:!", run(z, env))
    }
    case L(Op("1:"), z) => run(z, env) match {
      case L(a, b) => b
      case _ => Error("Wrong usage of 1:!", run(z, env))
    }


    /**
      *  General
      */
    case L (op, v) => {

      val v1 = run (op, env)
      val v2 = run (v, env)
      v1 match {
        //case Closure(x, exp, en) => run(L( L(Op(":="), L(x, v)), exp), en ++ env)
        case Closure(Var(x), exp, env_) => run(exp, env_+(x, v2, env_))
        case Error(s, L(Number(n), f)) => {
          var res = v2
          for (i <- 1 to n) res = run(L(f, res), env)
          res
        }
        case _ => Error("No operator!", L(v1, v2))
      }
    }

    case unknown => Error("What's this?", unknown)

  }// end run


  def pretty(v: SExp): String = v match {

    case L(a, b) => "(" + ((a, b) match {
      case (L(q, w), L(e, r)) => pretty(a) + " (" + pretty(b) + ")"
      case (L(a, s), d) => pretty(a) + " " + pretty(s) + " " + pretty(d)
      case _ => pretty(a) + " " + pretty(b)
    }) + ")"
    case Var(s) => s
    case Op(s) => s
    case Str(s) => s
    case Number(i) => i.toString
    case Closure(Var(s), e, env) => "(: " + s + " " + pretty(e) + ")"
    case Quote(s) => "(' " + pretty(s) + ")"
    case Nothing => "()"
    case Error(s, e) => "(" + "error " + "(" + s + ") " + pretty(e) + ")"
    case others => others.toString
  }

  def parse(src: SExp, env: List[Bind] = env0) = pretty(run(src, env))



} // end class Actuator



