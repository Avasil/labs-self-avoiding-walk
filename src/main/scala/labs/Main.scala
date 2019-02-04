package labs

import cats.effect._
import labs.EvalLoop.{CSVRecord, writeCSV}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      params <- IO.fromEither(Parameters.fromArgs(args))
      _ <- putStrLn(s"Running simulation with following parameters: $params")
      _ <- runMany(params, 1)
//      bestParams51 <- findBest(params.copy(length = 51), List(10000, 15000, 20000, 25000, 30000, 35000, 40000))
//      _ <- runMany(bestParams51, 10)
//      bestParams71 <- findBest(params.copy(length = 71), List(10000, 15000, 20000, 25000, 27500, 30000))
//      _ <- runMany(bestParams71, 10)
//      bestParams99 <- findBest(params.copy(length = 99), List(10000, 15000, 20000, 25000, 27500, 30000))
//      _ <- runMany(bestParams99, 10)
    } yield ExitCode.Success
  }

  def findBest(params: Parameters, maxIters: List[Int]): IO[Parameters] = {
    import cats.implicits._

    val runAll: IO[List[(Int, Vector[CSVRecord])]] =
      maxIters
        .map { iters =>
          val parameters = params.copy(iterations = iters)
          EvalLoop.runTimed(parameters).map(records => (parameters.iterations, records))
        }
        .sequence

    runAll.flatTap(content => IO(println(content))).map { results =>
      val (iters, _) = results.minBy({ case (_, records) => records.map(_.energy).sum / records.length })
      params.copy(iterations = iters)
    }
  }

  def runMany(params: Parameters, times: Int): IO[Unit] = {
    import cats.implicits._

    List.fill(times)(())
      .map(_ => EvalLoop.runTimed(params) >>= writeCSV(params))
      .sequence
      .void
  }
}