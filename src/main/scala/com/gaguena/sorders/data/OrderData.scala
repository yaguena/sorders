package com.gaguena.sorders.data

import java.time.LocalDateTime

import com.gaguena.sorders.persistence.model.Order
import com.gaguena.sorders.enums.PeriodEnum

case class OrderData(name: String, code: String, contact: String,
  shippingAddress: String, total: Int, createAt: LocalDateTime,
  items: Seq[ItemData])

case class OrderSomeData(name: String, code: String, contact: String, shippingAddress: String, total: Int)

object OrderData {
  def to(order: Order, items: Seq[ItemData]) = OrderData(order.name,
    order.code, order.contact, order.shippingAddress,
    order.total, order.createdAt, items)
}

case class OrderPeriod(period: PeriodEnum, total: Int)