import minitest.SimpleTestSuite

object EvalSuite extends SimpleTestSuite {
  test("eval") {
    assertEquals(2, 1 + 1)
  }
}