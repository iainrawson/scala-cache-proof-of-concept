package com.poc.cache

import cats.effect.IOApp
import cats.effect.IO
import io.chrisdavenport.mules.Cache
import io.chrisdavenport.mules.MemoryCache
import io.chrisdavenport.mules.TimeSpec
import scala.concurrent.duration.*

object Main extends IOApp.Simple {

  def run: IO[Unit] =

    val program = for
      c: Cache[IO, String, Int] <- MemoryCache
        .ofSingleImmutableMap[IO, String, Int](
          Some(TimeSpec.unsafeFromDuration(1.second))
        )
      _ <- CacheExample.insertAndGet(c).flatMap(IO.println)
    yield ()

    program

}
