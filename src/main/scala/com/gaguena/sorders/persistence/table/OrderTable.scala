package com.gaguena.sorders.persistence.table

import java.time.LocalDateTime
import com.gaguena.sorders.persistence.model.Order
import com.gaguena.sorders.persistence.table.DataBaseConversion._
import slick.driver.MySQLDriver.api._

class OrderTable(tag: Tag) extends Table[Order](tag, "orders") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("order_code")
  def name = column[String]("order_name")
  def contact = column[String]("order_contact")
  def shippingAddress = column[String]("order_shipping_address")
  def total = column[Int]("order_total")
  def createdAt = column[LocalDateTime]("created_at")
  def * = (id, code, name, contact, shippingAddress, total, createdAt) <> (Order.tupled, Order.unapply)
}
