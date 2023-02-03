package com.gaguena.sorders.persistence.model

import com.gaguena.sorders.data.ItemData

case class Item (id: Option[Long] = None, cost: BigDecimal, shippingFee: BigDecimal,
  taxAmount: BigDecimal, orderCode: String, productCode: String)

 object Items {
    def to(orderCode: String, data: ItemData) = Item(cost = data.cost,
      shippingFee = data.shippingFee, taxAmount = data.taxAmount, orderCode = orderCode, productCode = data.productCode)
}
