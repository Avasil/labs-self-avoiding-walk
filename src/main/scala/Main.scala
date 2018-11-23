import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      params <- IO.fromEither(Parameters.fromArgs(args))
      _ <- putStrLn(s"Running simulation with following parameters: $params")
      startingSequence = Sequence.gen(params.length)
      _ <- putStrLn(s"Starting sequence: $startingSequence")
      _ <- runTimed(startingSequence, params)
    } yield ExitCode.Success
  }

  def putStrLn(str: String): IO[Unit] = IO(println(str))

  def evalLoop(s: Sequence, iters: Int): IO[Unit] = {
    val newSequence = Sequence.sawSearch(s, iters)
    val energy = Evaluation.energy(newSequence)

    for {
      _ <- putStrLn(s"New Sequence: $newSequence\nEnergy: $energy")
      _ <- IO.cancelBoundary
      _ <- evalLoop(newSequence, iters)
    } yield ()
  }

  def runTimed(s: Sequence, params: Parameters): IO[Unit] = {
    IO.race(
      evalLoop(s, params.iterations),
      IO.sleep(params.time)
    ).void
  }
}