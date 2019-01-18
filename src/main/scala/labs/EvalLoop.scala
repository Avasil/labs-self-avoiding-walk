package labs

import java.io.File

import cats.effect.concurrent.Ref
import cats.effect.{ContextShift, IO, Timer}
import purecsv.unsafe._

object EvalLoop {

  def runTimed(params: Parameters)(implicit cs: ContextShift[IO], timer: Timer[IO]): IO[Vector[CSVRecord]] = {
    val firstSequence = Sequence.gen(params.length)
    for {
      initialResult <- showResult(firstSequence, System.currentTimeMillis())
      // shared state for saving csv lines
      ref <- Ref[IO].of[Vector[CSVRecord]](Vector(initialResult))
      _ <- IO.race(
        evalLoop(firstSequence, params.iterations, params.length, System.currentTimeMillis(), ref),
        IO.sleep(params.time)
      )
      csvRecords <- ref.get
    } yield csvRecords
  }

  def writeCSV(params: Parameters)(records: Vector[CSVRecord]): IO[Unit] = IO {
    val file = new File(s"output/${params.length}-${params.iterations}-${params.time.toSeconds}-${System.currentTimeMillis()}.csv")
    if ((file.getParentFile.mkdirs() || file.getParentFile.exists()) && file.createNewFile()) {
      println("Writing results to CSV file.")
      records.writeCSVToFile(file)
    } else {
      println(s"Couldn't write to ${file.getAbsolutePath}")
    }
  }

  private def evalLoop(s: Sequence, maxIters: Int, length: Int, startedTime: Long,
                       ref: Ref[IO, Vector[CSVRecord]]): IO[Unit] = {
    val newSequence = Sequence.sawSearch(s, maxIters)

    for {
      csvRecord <- showResult(newSequence, startedTime)
      _ <- ref.update(_ :+ csvRecord)
      _ <- IO.cancelBoundary
      _ <- evalLoop(Sequence.gen(length), maxIters, length, startedTime, ref)
    } yield ()
  }

  private def showResult(s: Sequence, startedTime: Long): IO[CSVRecord] =
    for {
      energy <- IO.pure(Evaluation.energy(s))
      currentTime <- IO((System.currentTimeMillis() - startedTime) / 1000)
      _ <- putStrLn(s"E: $energy - $currentTime [s] - $s")
    } yield CSVRecord(currentTime, energy, s.toString)

  case class CSVRecord(time: Long, energy: Int, sequence: String)
}