package com.poc.cache

import cats.effect.IOApp
import cats.effect.IO


object Main extends IOApp.Simple {

  def run: IO[Unit] =
    CacheExample.insertAndGet.flatMap(IO.println)

}
