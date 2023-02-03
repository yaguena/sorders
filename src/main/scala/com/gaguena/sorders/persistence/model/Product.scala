package com.gaguena.sorders.persistence.model

import java.time.LocalDateTime
import com.gaguena.sorders.data.ProductData

case class Product (id: Option[Long] = None,
  code: String, name: String, category: String, price: BigDecimal,
  createdAt: LocalDateTime = LocalDateTime.now())

object Products {
  def to(data: ProductData) = Product(code = data.code,
    name = data.name, category = data.category, price = data.price)
}