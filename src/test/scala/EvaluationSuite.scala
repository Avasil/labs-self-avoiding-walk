import Evaluation._
import minitest.SimpleTestSuite

object EvaluationSuite extends SimpleTestSuite {
  test("reversing order doesn't change evaluation") {
    val s1 = Vector(1, 1, 1, 0, 1, 0, 0, 1)
    val s2 = s1.reverse

    val e1 = energy(s1)
    val e2 = energy(s2)

    assertEquals(e1, e2)
    assertEquals(e1, 8)
  }

  test("negating sequence doesn't change evaluation") {
    val s1 = Vector(1, 1, 1, 0, 1, 0, 0, 1)
    val s2 = Vector(0, 0, 0, 1, 0, 1, 1, 0)

    val e1 = energy(s1)
    val e2 = energy(s2)

    assertEquals(e1, e2)
    assertEquals(e1, 8)
  }

  test("negating every 2nd element doesn't change evaluation") {
    val s1 = Vector(1, 1, 1, 0, 1, 0, 0, 1)
    val s2 = Vector(0, 1, 0, 0, 0, 0, 1, 1)

    val e1 = energy(s1)
    val e2 = energy(s2)

    assertEquals(e1, e2)
    assertEquals(e1, 8)
  }

  test("small changes in sequence cause big change in evaluation") {
    val s1 = Vector(1, 0, 1, 0, 1, 0, 1, 0)
    val s2 = Vector(1, 0, 1, 0, 1, 0, 1, 1)
    val s3 = Vector(1, 0, 1, 0, 1, 0, 0, 1)
    val s4 = Vector(1, 1, 1, 0, 1, 0, 0, 1)

    val e1 = energy(s1)
    val e2 = energy(s2)
    val e3 = energy(s3)
    val e4 = energy(s4)

    val received = List(e1, e2, e3, e4)
    val expected = List(140, 56, 36, 8)

    assertEquals(received, expected)
  }
}