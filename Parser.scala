package parser



import scala.util._


trait SExp
trait Value extends SExp
case object Nothing extends Value
case class Error(msg: String, e: SExp) extends Value
case class Number(i: Int) extends Value
case class Var(s: String) extends SExp
case class Symbol(s: String) extends Value
case class Str(s: String) extends Value
case class Op(op: String) extends SExp
case class L(op: SExp, x: SExp*) extends SExp



class Parser {

  type Bind = (String, Value)

  def env0: List [Bind] = Nil
  def env_+ (s: String, v: Value, env: List [Bind]): List [Bind] =
    (s, v) :: env
  private def lookup (name: String, env: List [Bind]): Option [Value] =
    env match {
      case (x :: xs)  => if (x._1 == name) Some (x._2) else lookup (name, xs)
      case _ => None
    }

  case class Closure (x: Var, e: SExp, env: List [Bind]) extends Value


  def run (e: SExp, env: List [Bind]): Value = e match {

    case Number(i) => Number(i)
    case Symbol(s) => Symbol(s)
    case Closure(v, e, env) => Closure(v, e, env)
    case Var("") => Nothing
    case Var(s) => lookup (s, env) match {
      case Some (x) => x
      case None  => Error("No such variable!", Var(s))
    }

    /**
      * Bindings
      */
    case L( L(Op (":="), L(Var (s), e)),
            exp) => run (exp, env_+ (s, run (e, env), env))

    /**
      *  Conditions
      */
    case L(L(Op("if"), e), L(e0, e1)) => run(e, env) match {

      case Symbol("true") => run(e0, env)
      case Symbol("false") => run(e1, env)
      case _ => Error("Wrong if function!", L(L(Op("if"), e), L(e0, e1)))
    }

    /**
      *  Support for church numeral
      */
    case L (L(Number(n), op), v) => {

      var res: Value = run(v, env)
      for (i <- 1 to n) {
        res =  run(L(op, res), env)
      }
      res
    }


    case L(L(Op("+"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m + n)
      case _ => Error("", L(Symbol("+?"), L(run(x, env), run(y, env))))
    }
    case L(L(Op("-"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m - n)
      case _ => Error("", L(Symbol("-?"), L(x, y)))
    }
    case L(L(Op("*"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m * n)
      case _ => Error("", L(Symbol("*?"), L(x, y)))
    }
    case L(L(Op("/"), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => Number (m / n)
      case _ => Error("", L(Symbol("/?"), L(x, y)))
    }
    case L(L(Op("="), x), y) => (run (x, env), run (y, env)) match {
      case (Number (m), Number (n)) => if (m == n) Symbol("true") else Symbol("false")
      case _ => Error("", L(Symbol("=?"), L(x, y)))
    }
    case L(Op("'"), Var(s)) => Symbol(s)
    case L(L(Op(":"), Var(s)), e) => Closure(Var(s), e, env)
    case L(Op("print"), e) => {val res = run(e, env); print(pretty(res)); Nothing}


    /**
      *  General
      */
    case L (op, v) => {

      val v1 = run (op, env)
      val v2 = run (v, env)
      v1 match {
        case Closure (x, exp, env) => run(L( L(Op(":="), L(x, v2)), exp), env)
        case _ => Error("No operator!", L(v1, v2))
      }
    }

    case unknown => Error("What's this?", unknown)

  } // end run


  def pretty(v: SExp): String = v match {

    case L(op, e) => "(" + pretty(op) + " " + pretty(e) + ")"
    case Var(s) => s
    case Op(s) => s
    case Number(i) => i.toString
    case Closure(Var(s), e, env) => "(lambda " + s + " " + pretty(e) + ")"
    case Symbol(s) => "(' " + s + ")"
    case Nothing => "()"
    case Error(s, e) => "(" + "error " + "(\"" + s + ") " + pretty(e) + ")"
    case others => others.toString
  }

  def parse(src: SExp) = pretty(run(src, env0))



} // end class Actuator



