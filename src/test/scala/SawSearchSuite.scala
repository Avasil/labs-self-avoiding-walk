import labs.{Evaluation, Sequence}
import minitest.SimpleTestSuite

object SawSearchSuite extends SimpleTestSuite {
  test("works #1") {
    val s1 = Vector(0, 1, 1, 1, 0, 1, 0, 0, 1, 0)

    val best = Sequence.sawSearch(s1, 10)
    val expectedEnergy = 13

    assertEquals(Evaluation.energy(best), expectedEnergy)
  }

  test("works #2") {
    val s1 = Vector(0, 0, 1, 1, 1, 0, 0, 0, 0, 0)

    val best = Sequence.sawSearch(s1, 10)
    val expectedEnergy = 13

    assertEquals(Evaluation.energy(best), expectedEnergy)
  }
}