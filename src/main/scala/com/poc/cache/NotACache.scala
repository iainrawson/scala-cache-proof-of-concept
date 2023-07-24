package com.poc.cache

import cats.*
import cats.implicits.*
import dev.profunktor.redis4cats.algebra.KeyCommands
import dev.profunktor.redis4cats.algebra.StringCommands
import dev.profunktor.redis4cats.effects.*

import io.chrisdavenport.mules.Cache
import io.chrisdavenport.mules.TimeSpec
import scala.concurrent.duration.*
import cats.effect.kernel.Async

object NotACache {

  def of[F[_]: Applicative, K, V](): Cache[F, K, V] =
    new NotACacheImpl[F, K, V]()

  private class NotACacheImpl[F[_]: Applicative, K, V]() extends Cache[F, K, V] {
    def insert(k: K, v: V): F[Unit] = ().pure[F]
    def lookup(k: K): F[Option[V]] = None.pure[F]
    def delete(k: K): F[Unit] = ().pure[F]
  }

}
