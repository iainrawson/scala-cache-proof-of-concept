package com.poc.cache

import cats.effect.IO
import io.chrisdavenport.mules.*
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object CacheExample {

  def insertAndGet(cache: Cache[IO, String, Int]): IO[Option[Int]] =
    for {
        _     <- cache.insert("Foo", 1)
        value <- cache.lookup("Foo")
      } yield value

}
