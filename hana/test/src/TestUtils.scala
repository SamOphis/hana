import hana.syntax.Parser.expr
import fastparse._

object TestUtils {
  def extract[T](code: String, parser: P[_] => P[_] = expr(_)): T = {
    val Parsed.Success(value, _) = parse(code, parser)
    value.asInstanceOf[T]
  }
}