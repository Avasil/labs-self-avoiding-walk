case class Sequence private(s: Vector[Int]) extends AnyVal {
  def length: Int = s.length

  def get(i: Int) = s(i)

  def reverse = new Sequence(s.reverse)
}

object Sequence {
  def apply(s: Vector[Int]): Sequence =
    new Sequence(s.map {
      case 0 => -1
      case 1 => 1
      case -1 => -1
    })

  // if we try to pass `Vector[Int]` to function
  // that requires `Sequence` it will be automatically
  // converted if this function is in scope :)
  implicit def vec2seq(s: Vector[Int]): Sequence = apply(s)
}