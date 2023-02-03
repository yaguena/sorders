package com.gaguena.sorders.persistence.table

import java.time.LocalDateTime
import com.gaguena.sorders.persistence.model.Product
import com.gaguena.sorders.persistence.table.DataBaseConversion._
import slick.driver.MySQLDriver.api._

class ProductTable(tag: Tag) extends Table[Product](tag, "products") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("product_code")
  def name = column[String]("product_name")
  def category = column[String]("product_category")
  def price = column[BigDecimal]("product_price")
  def createdAt = column[LocalDateTime]("created_at")
  def * = (id, code, name, category, price, createdAt) <> (Product.tupled, Product.unapply)
}

