import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      params <- IO.fromEither(Parameters.fromArgs(args))
      _ <- IO(println(s"Running simulation with following parameters: $params"))
      startingSequence = Sequence.gen(params.length)
      _ <- IO(println(s"Starting sequence: ${startingSequence.toBinaryVector}"))
    } yield ExitCode.Success
  }
}