package labs

import scala.concurrent.duration._
import scala.util.Try

case class Parameters(length: Int, iterations: Int, time: FiniteDuration)

object Parameters {
  def fromArgs(args: List[String]): Either[IllegalArgumentException, Parameters] =
    args match {
      case length :: iterations :: time :: _ =>
        val params: Try[Parameters] =
          for {
            l <- Try(length.toInt)
            iters <- Try(iterations.toInt)
            t <- Try(time.toInt.second)
          } yield Parameters(l, iters, t)

        params
          .fold(
            ex => Left(new IllegalArgumentException(s"Couldn't parse arguments:\n${ex.getMessage}")),
            parameters => Right(parameters)
          )
      case _ =>
        Left(new IllegalArgumentException("Missing parameters: should provide length, iterations and time in seconds."))
    }
}