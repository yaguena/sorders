package com.gaguena.sorders.persistence.table

import java.time.LocalDateTime
import com.gaguena.sorders.persistence.table.DataBaseConversion._
import slick.driver.MySQLDriver.api._
import com.gaguena.sorders.persistence.model.Item

class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def cost = column[BigDecimal]("item_cost")
  def shippingFee = column[BigDecimal]("item_shipping_fee")
  def taxAmount = column[BigDecimal]("item_tax_amount")
  def orderCode = column[String]("order_code")
  def productCode = column[String]("product_code")
  def * = (id, cost, shippingFee, taxAmount, orderCode, productCode) <> (Item.tupled, Item.unapply)
}
