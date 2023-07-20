package com.poc.cache

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite

class CacheExampleSuite extends CatsEffectSuite {

  test("cache example returns Some(1)") {
    CacheExample.insertAndGet.map(it => assertEquals(it, Some(1)))
  }
}
