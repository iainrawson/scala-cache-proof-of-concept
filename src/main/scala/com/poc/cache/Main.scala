package com.poc.cache

import cats.effect.IOApp
import cats.effect.IO
import io.chrisdavenport.mules.Cache
import io.chrisdavenport.mules.MemoryCache
import io.chrisdavenport.mules.TimeSpec
import scala.concurrent.duration.*
import com.poc.cache.CacheService

object Main extends IOApp.Simple {

  def run: IO[Unit] =

    val program = for
      c: Cache[IO, String, String] <- MemoryCache
        .ofSingleImmutableMap[IO, String, String](
          Some(TimeSpec.unsafeFromDuration(1.second))
        )
      _ <- CacheService.insertAndGet(c).flatMap(IO.println)
    yield ()

    program

}
