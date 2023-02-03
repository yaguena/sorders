package com.gaguena.sorders.persistence.model

import java.time.LocalDateTime
import com.gaguena.sorders.data.OrderData

case class Order(id: Option[Long] = None,
    code: String, name: String, contact: String,
    shippingAddress: String, total: Int,
    createdAt: LocalDateTime = LocalDateTime.now())

object Orders {
  def to(orderData: OrderData) =
    new Order(code = orderData.code,
      contact = orderData.contact ,
      name = orderData.name,
      shippingAddress = orderData.shippingAddress,
      total = orderData.total)
}
