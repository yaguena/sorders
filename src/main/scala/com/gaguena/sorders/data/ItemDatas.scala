package com.gaguena.sorders.data

import com.gaguena.sorders.persistence.model.Item

case class ItemData(cost: BigDecimal, shippingFee: BigDecimal,
  taxAmount: BigDecimal, orderCode: String, productCode: String,
  products: Seq[ProductData] = Seq.empty)

object ItemData {
  def to(item: Item) = ItemData(item.cost, item.shippingFee, item.taxAmount,
    item.orderCode, item.productCode)

  def to(item: Item, products: List[ProductData] ) = ItemData(item.cost,
    item.shippingFee, item.taxAmount, item.orderCode,
    item.productCode, products)
}