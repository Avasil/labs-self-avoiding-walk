package labs

import cats.effect.{ContextShift, IO, Timer}
import cats.syntax.all._

object EvalLoop {

  def runTimed(s: Sequence, params: Parameters)(implicit cs: ContextShift[IO], timer: Timer[IO]): IO[Unit] = {
    printResult(s, System.currentTimeMillis()) *>
      IO.race(
        evalLoop(s, params.iterations, System.currentTimeMillis()),
        IO.sleep(params.time)
      ).void
  }

  private def evalLoop(s: Sequence, maxIters: Int, startedTime: Long): IO[Unit] = {
    val newSequence = Sequence.sawSearch(s, maxIters)

    for {
      _ <- printResult(newSequence, startedTime)
      _ <- IO.cancelBoundary
      _ <- evalLoop(newSequence, maxIters, startedTime)
    } yield ()
  }

  private def printResult(s: Sequence, startedTime: Long): IO[Unit] = {
    val energy = Evaluation.energy(s)
    val currentTime = (System.currentTimeMillis() - startedTime) / 1000
    putStrLn(s"$energy         $currentTime         $s")
  }
}