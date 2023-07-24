package com.poc.cache

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite
import io.chrisdavenport.mules.{MemoryCache, TimeSpec, Cache}
import scala.concurrent.duration.*
import cats.effect.kernel.Resource

import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.typelevel.log4cats.Logger

import dev.profunktor.redis4cats.connection.{RedisURI, RedisClient}
import dev.profunktor.redis4cats.data.RedisCodec
import dev.profunktor.redis4cats.algebra.StringCommands
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.Stdout.*

import com.poc.cache.RedisCache

class CacheExampleSuite extends CatsEffectSuite {

  test("CacheService can return cached result using MemoryCache") {

    val program = for
      c: Cache[IO, String, String] <- MemoryCache
        .ofSingleImmutableMap[IO, String, String](
          Some(TimeSpec.unsafeFromDuration(1.second))
        )
      result <- CacheService.insertAndGet(c)
    yield result

    assertIO(program, Some("bar"))

  }

  test("CacheService can return cached result using Redis") {

    // redis4cats requires a logger
    implicit val logger: Logger[IO] = Slf4jLogger.getLogger[IO]

    // Create a RedisClient (redis4cats) wrapped in a Cache (mules)
    val cacheResource = for
      redis <- Redis[IO].utf8("redis://localhost:6379")
      cache = RedisCache.fromCommands(redis, defaultTimeout = None)
    yield cache

    cacheResource
      .use { cache =>
        val result = CacheService.insertAndGet(cache)
        assertIO(result, Some("bar"))
      }

  }

  test("CacheService can return None using NotACache") {


    val c: Cache[IO, String, String] = NotACache.of[IO, String, String]()

    val program = for
      result <- CacheService.insertAndGet(c)
    yield result

    assertIO(program, None)

  }

}
