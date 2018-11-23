package labs

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      params <- IO.fromEither(Parameters.fromArgs(args))
      _ <- putStrLn(s"Running simulation with following parameters: $params")
      startingSequence = Sequence.gen(params.length)
      _ <- putStrLn(s"Energy     Time [s]  labs.Sequence")
      _ <- EvalLoop.runTimed(startingSequence, params)
    } yield ExitCode.Success
  }
}