package com.gaguena.sorders.service

import com.gaguena.sorders.data.ProductData
import com.gaguena.sorders.persistence.model.Products
import com.gaguena.sorders.repository.ProductRepository
import com.gaguena.sorders.repository.ProductRepositoryComponent
import com.gaguena.sorders.repository.ProductRepository

trait ProductServiceComponent {
  val productService = new ProductService;
}

class ProductService extends ProductRepositoryComponent {
  def create(data: ProductData) = {
    val product = Products.to(data)
    productRepository.save(product)
  }
}