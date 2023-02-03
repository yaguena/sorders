package com.gaguena.sorders.repository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._
import com.gaguena.sorders.persistence.model.Order
import com.gaguena.sorders.persistence.table.OrderTable
import java.time.LocalDateTime
import com.gaguena.sorders.persistence.table.DataBaseConversion._

class OrderRepository extends Repository[Order] {

  lazy val all = TableQuery[OrderTable]

  def save(order: Order): Future[Order] =
    transactionally((all.returning(all.map(_.id)))
      .insertOrUpdate(order)
      .map(_.map(id => order.copy(id = id))
      .getOrElse(order)))

  def findBy(start: LocalDateTime, end: LocalDateTime): Future[List[Order]] =
    transactionally(all.filter(item => item.createdAt > start && item.createdAt < end)
      .result.map(_.toList))

  def findBy(date: LocalDateTime): Future[List[Order]] =
    transactionally(all.filter(item => item.createdAt <= date)
      .result.map(_.toList))

  def createSchema() = db.run(DBIO.seq(all.schema.create))
}