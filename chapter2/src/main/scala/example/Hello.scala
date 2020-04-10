package example

import cats.implicits._
import io.estatico.newtype.macros._

import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.Contains

import cats.effect.concurrent.Ref
import cats.effect.Sync

import cats.data.State

object Hello extends Greeting with App {
  // Smart constructors
  @newtype case class Username(value: String)
  @newtype case class Email(value: String)
 
  println(greeting)

  def mkUsername(value: String): Option[Username] =
    if (value.nonEmpty) Username(value).some
    else none[Username]

  def mkEmail(value: String): Option[Email] = 
    if (value.contains("@")) Email(value).some
    else none[Email]

  // ( mkUsername("chiroptical"), mkEmail("hello@email.com") ).mapN { case (username, email) => lookup(username, email) }

  // def lookup(val username: NonEmptyString): F[Option[User]]
  
  // type Username = String Refined Contains ['g']
  // def lookup(username: Username): F[Option[User]]

}

trait Counter[F[_]] {
  def increment: F[Unit]
  def get: F[Int] 
}

// class LiveCounter[F[_]] private (ref: Ref[F, Int]) extends Counter[F] {
//   // (`+` 1)
//   def increment: F[Unit] = ref.update(_ + 1)
//   def get: F[Int] = ref.get
// }

object LiveCounter {
  def make[F[_]: Sync]: F[Counter[F]] = Ref.of[F, Int](0).map(
    ref => new Counter[F] {
      def increment: F[Unit] = ref.update(_ + 1)
      def get: F[Int] = ref.get
    }
  )

  // def program(counter: Counter[IO]): IO[Unit] =
  //   counter.incr *> other_stuff
}

object StateExample {
  val nextInt: State[Int, Int] = State(s => (s + 1, s * 2))
  def seq: State[Int, Int] =
    for {
      n1 <- nextInt
      n2 <- nextInt
      n3 <- nextInt
    } yield n1 + n2 + n3
}

trait Greeting {
  lazy val greeting: String = "hello"
}

