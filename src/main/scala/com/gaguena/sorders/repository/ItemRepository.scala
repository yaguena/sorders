package com.gaguena.sorders.repository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._
import com.gaguena.sorders.persistence.table.ItemTable
import com.gaguena.sorders.persistence.model.Item

class ItemRepository  extends Repository[Item] {

  lazy val all = TableQuery[ItemTable]

  def findBy(code: String) =
    transactionally(all.filter(item => item.orderCode === code).result.map(_.toList))

  def save(item: Item): Future[Item] =
    transactionally((all.returning(all.map(_.id)))
      .insertOrUpdate(item)
      .map(_.map(id => item.copy(id = id))
      .getOrElse(item)))

  def createSchema() = db.run(DBIO.seq(all.schema.create))

}