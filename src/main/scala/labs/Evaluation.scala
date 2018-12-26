package labs

import scala.math.pow

object Evaluation {
  // (2.1)
  def autocorrelation(s: Sequence, k: Int): Int = {
    val l = s.length
    Stream.range(0, l - k, 1)
      .foldLeft(0)((sum, i) => sum + s(i) * s(i + k))
  }

  // (2.2)
  def energy(s: Sequence): Int =
    Stream.range(1, s.length)
      .foldLeft(0)((sum, k) => sum + pow(autocorrelation(s, k), 2).toInt)

  // (2.3)
  def meritFactor(s: Sequence): Double =
    pow(s.length, 2) / (2 * energy(s))
}