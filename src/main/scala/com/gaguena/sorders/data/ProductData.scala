package com.gaguena.sorders.data

import java.time.LocalDateTime
import com.gaguena.sorders.persistence.model.Product

case class ProductData(code: String, name: String, category: String, price: BigDecimal,
  createdAt: LocalDateTime = LocalDateTime.now())

object ProductData {
  def to(product: Product) = ProductData(product.code,
    product.name, product.category, product.price, product.createdAt)
}