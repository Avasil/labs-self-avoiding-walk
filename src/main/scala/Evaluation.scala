import scala.math.pow

object Evaluation {
  // (2.1)
  def autocorrelation(s: Sequence, k: Int): Int = {
    val l = s.length
    (0 until l - k)
      .map { i => s.get(i) * s.get(i + k) }
      .sum
  }

  // (2.2)
  def energy(s: Sequence): Int =
    (1 until s.length)
      .map(k => pow(autocorrelation(s, k), 2).toInt)
      .sum

  // (2.3)
  def meritFactor(s: Sequence): Double =
    pow(s.length, 2) / (2 * energy(s))
}