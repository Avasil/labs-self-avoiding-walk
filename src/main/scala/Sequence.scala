import scala.util.Random

case class Sequence private(s: Vector[Int]) extends AnyVal {
  def length: Int = s.length

  def get(i: Int) = s(i)

  def reverse = new Sequence(s.reverse)

  def updated(index: Int, elem: Int) = new Sequence(s.updated(index, elem))

  def toBinaryVector: Vector[Int] = s.map {
    case -1 => 0
    case x => x
  }
}

object Sequence {
  def apply(s: Vector[Int]): Sequence =
    new Sequence(s.map {
      case 0 => -1
      case 1 => 1
      case -1 => -1
      case _ =>
        throw new IllegalArgumentException("Sequence needs to be binary vector (either {0, 1} or {-1, 1}")
    })

  def gen(size: Int): Sequence = {
    val vector = Vector.fill(size)(Random.nextInt(2))

    Sequence(vector)
  }

  // if we try to pass `Vector[Int]` to function
  // that requires `Sequence` it will be automatically
  // converted if this function is in scope :)
  implicit def vec2seq(s: Vector[Int]): Sequence = apply(s)

  // (3.6)
  def sawSearch(s: Sequence, maxIters: Int): Sequence = {
    var walkList = Set(s)
    var Si = s
    var Sbest = s
    var Fbest = Evaluation.meritFactor(Sbest)

    (0 until maxIters).foreach { _ =>
      var Fi = Double.MinValue

      (0 until s.length).foreach { j =>
        val Stmp = Si.updated(j, -1 * Si.get(j))
        val Ftmp = Evaluation.meritFactor(Stmp)

        if (Ftmp > Fi && !walkList.contains(Stmp)) {
          Si = Stmp
          Fi = Ftmp
        }
      }
      if (!walkList.contains(Si)) {
        walkList = walkList + Si
        if (Fi > Fbest) {
          Sbest = Si
          Fbest = Fi
        }
      } else {
        return Sbest
      }
    }
    Sbest
  }
}