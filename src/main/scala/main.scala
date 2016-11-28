import scala.util.{Either, Right, Left}

case class Failed(msg: String)

object UnitExample {

  def write(): Either[Failed, Unit] = {
    Right( () )
  }

  def log(): Either[Failed, Unit] = {
    Left(Failed("during log"))
  }

  def cleanup(): Unit = {}

  def run(): Either[Failed, Unit] =
   write().map(_ => log())
}

object MarkerExample {

  sealed trait Success
  object Success extends Success

  def write(): Either[Failed, Success] = {
    Right(Success)
  }

  def log(): Either[Failed, Success] = {
    Left(Failed("in log"))
    //Right(success)
  }

  def run(): Either[Failed, Success] =
    for {
      _ <- write()
      _ <- log()
    } yield Success

  def run0(): Either[Failed, Success] = {
    write().flatMap(_ => log())
    // Hurrah! Wont' compile -> write().map(_ => log())
  }
}

object Main {

  def main(args: Array[String]): Unit = {
    println("\nUnit example:")
    println( UnitExample.run() )
    println("\n\nMarker-based example:")
    println( MarkerExample.run() )
    println("\n")
  }

}
