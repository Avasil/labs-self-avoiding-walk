import cats.effect.IO

package object labs {
  def putStrLn(str: String): IO[Unit] = IO(println(str))
}
