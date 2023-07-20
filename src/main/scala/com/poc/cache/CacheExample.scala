package com.poc.cache

import cats.effect.IO
import io.chrisdavenport.mules.*
import cats.effect.IO
import scala.concurrent.duration.*
import cats.effect.unsafe.implicits.global

object CacheExample {

  def insertAndGet: IO[Option[Int]] =
    for {
        cache <- MemoryCache.ofSingleImmutableMap[IO, String, Int](Some(TimeSpec.unsafeFromDuration(1.second)))
        _     <- cache.insert("Foo", 1)
        value <- cache.lookup("Foo")
      } yield value

}
