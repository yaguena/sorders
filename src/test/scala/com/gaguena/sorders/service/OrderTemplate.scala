package com.gaguena.sorders.service

import java.time.LocalDateTime

import com.gaguena.sorders.persistence.model.{ Item, Order, Product }
import java.time.format.DateTimeFormatter

object OrderTemplate {
  def orders = {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val date = LocalDateTime.parse("2022-12-31 00:00", format)
    List(
      Order(code = "1", name = "Tests", contact = "tests", shippingAddress = "tests", total = 1, createdAt = date),
      Order(code = "2", name = "Tests", contact = "tests", shippingAddress = "tests", total = 1, createdAt = date.minusMonths(3)),
      Order(code = "3", name = "Tests", contact = "tests", shippingAddress = "tests", total = 1, createdAt = date.minusMonths(6))
    )
  }

  def items = List(
    Item(cost = 21, shippingFee=1, taxAmount=3, orderCode = "1", productCode="10001"),
    Item(cost = 33, shippingFee=3, taxAmount=4, orderCode = "1", productCode="10002"),
    Item(cost = 33, shippingFee=3, taxAmount=4, orderCode = "2", productCode="10003"),
    Item(cost = 21, shippingFee=1, taxAmount=3, orderCode = "2", productCode="10004"),
    Item(cost = 33, shippingFee=3, taxAmount=4, orderCode = "3", productCode="10004"),
    Item(cost = 33, shippingFee=3, taxAmount=4, orderCode = "3", productCode="10005")
  )

  def products = {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val date = LocalDateTime.parse("2022-12-31 00:00", format)
    List(
      Product(code = "10001", name = "Panela de aco", category = "PANELAS", price = 23.02, createdAt = date.minusMonths(1)),
      Product(code = "10002", name = "Panela de Barro", category = "PANELAS", price = 31.02, createdAt = date.minusMonths(2)),
      Product(code = "10003", name = "Panela de Barro", category = "PANELAS", price = 31.02, createdAt = date.minusMonths(5)),
      Product(code = "10004", name = "Panela de Aluminio", category = "PANELAS", price = 23.02, createdAt = date.minusMonths(7)),
      Product(code = "10005", name = "Alicate", category = "Materiais", price = 31.02, createdAt = date.minusMonths(13))
    )
  }
}