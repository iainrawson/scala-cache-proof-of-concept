package com.poc.cache

import cats.effect.IO
import io.chrisdavenport.mules.*
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object CacheService:
  def insertAndGet(cache: Cache[IO, String, String]): IO[Option[String]] =
    for {
      _ <- cache.insert("foo", "bar")
      value <- cache.lookup("foo")
    } yield value