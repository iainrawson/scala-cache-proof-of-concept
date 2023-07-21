package com.poc.cache

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite
import io.chrisdavenport.mules.MemoryCache
import io.chrisdavenport.mules.TimeSpec
import cats.effect.IOApp
import cats.effect.IO
import io.chrisdavenport.mules.Cache
import io.chrisdavenport.mules.MemoryCache
import io.chrisdavenport.mules.TimeSpec
import scala.concurrent.duration.*

class CacheExampleSuite extends CatsEffectSuite {

  test("CacheExample returns Some(1) using MemoryCache") {

    val program = for
      c: Cache[IO, String, Int] <- MemoryCache.ofSingleImmutableMap[IO, String, Int](Some(TimeSpec.unsafeFromDuration(1.second)))
      result <- CacheExample.insertAndGet(c)
    yield result

    assertIO(program, Some(1))
    
  }
}
