import hana.define.Literals._
import utest._
import TestUtils._
import scala.collection.{Map => ScalaMap}

object SuccessfulParserTests extends TestSuite {
  val tests: Tests = Tests {
    test("string") { Str("Hello, world!") ==> extract("\"Hello, world!\"") }
    test("identifier") { Ident("_a1") ==> extract("_a1") }

    test("numbers") {
      test("integer") { Num(123) ==> extract("123")}
      test("big_integer") { Num(Double.PositiveInfinity) ==> extract("1".repeat(400))}
      test("decimal") { Num(0.5) ==> extract("0.5") }
    }

    test("maps") {
      test("empty_map") { Map(ScalaMap.empty) ==> extract("{}") }
      test("occupied_map") { Map(ScalaMap(Str("foo") -> Ident("bar"), Ident("abc") -> Num(54321))) ==>
        extract("{abc -> 54321, \"foo\" -> bar}") }
    }

    test("lists") {
      test("empty_list")  { List(Seq.empty) ==> extract("[]") }
      test("occupied_list")  { List(Seq(Num(1), Str("foo"), Ident("bar"), Map(ScalaMap.empty))) ==>
        extract("[1, \"foo\", bar, {}]") }
    }
  }
}