package labs

import java.io.File

import cats.effect.concurrent.Ref
import cats.effect.{ContextShift, IO, Timer}
import purecsv.unsafe._

object EvalLoop {
  def runTimed(s: Sequence, params: Parameters)(implicit cs: ContextShift[IO], timer: Timer[IO]): IO[Unit] = {
    for {
      _ <- showResult(s, System.currentTimeMillis())
      // shared state for saving csv lines
      ref <- Ref[IO].of[Vector[CSVRecord]](Vector())
      _ <- IO.race(
        evalLoop(s, params.iterations, System.currentTimeMillis(), ref),
        IO.sleep(params.time)
      )
      csvRecords <- ref.get
      _ <- writeCSV(csvRecords)
    } yield ()
  }

  private def evalLoop(s: Sequence, maxIters: Int, startedTime: Long, ref: Ref[IO, Vector[CSVRecord]]): IO[Unit] = {
    val newSequence = Sequence.sawSearch(s, maxIters)

    for {
      csvRecord <- showResult(newSequence, startedTime)
      _ <- ref.update(_ :+ csvRecord)
      _ <- IO.cancelBoundary
      _ <- evalLoop(newSequence, maxIters, startedTime, ref)
    } yield ()
  }

  private def showResult(s: Sequence, startedTime: Long): IO[CSVRecord] =
    for {
      energy <- IO.pure(Evaluation.energy(s))
      currentTime <- IO((System.currentTimeMillis() - startedTime) / 1000)
      _ <- putStrLn(s"E: $energy - $currentTime [s] - $s")
    } yield CSVRecord(currentTime, energy, s.toString)

  case class CSVRecord(time: Long, energy: Int, sequence: String)

  private def writeCSV(records: Seq[CSVRecord]): IO[Unit] = IO {
    val file = new File(s"output/${System.currentTimeMillis()}.csv")
    if ((file.getParentFile.mkdirs() || file.getParentFile.exists()) && file.createNewFile()) {
      println("Writing results to CSV file.")
      records.writeCSVToFile(file)
    } else {
      println(s"Couldn't write to ${file.getAbsolutePath}")
    }
  }
}