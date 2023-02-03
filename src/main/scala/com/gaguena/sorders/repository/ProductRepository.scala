package com.gaguena.sorders.repository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._
import com.gaguena.sorders.persistence.model.Product
import com.gaguena.sorders.persistence.table.ProductTable

trait ProductRepositoryComponent {
  val productRepository = new ProductRepository
}

class ProductRepository  extends Repository[Product] {

  lazy val all = TableQuery[ProductTable]

  def save(product: Product): Future[Product] =
    transactionally((all.returning(all.map(_.id)))
      .insertOrUpdate(product)
      .map(_.map(id => product.copy(id = id))
      .getOrElse(product)))

  def findBy(codes: Seq[String]) =
    transactionally(all.filter(product => product.code inSet codes).result.map(_.toList))

  def createSchema() = db.run(DBIO.seq(all.schema.create))

}