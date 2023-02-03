package com.gaguena.sorders.service

import java.time.LocalDateTime

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import org.mockito.Mockito.when
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.specs2.matcher.FutureMatchers
import org.specs2.mock.Mockito

import com.gaguena.sorders.repository.{ ItemRepository, OrderRepository, ProductRepository }
import java.time.format.DateTimeFormatter
import com.gaguena.sorders.enums.PeriodEnum

class OrdeServiceTest extends AnyFunSuite  with FutureMatchers  with Mockito with Matchers { 
  val orderRepositoryMock = mock[OrderRepository]
  val itemRepositoryMock = mock[ItemRepository]
  val productRepositoryMock = mock[ProductRepository]

  val orderService = new OrderService() {
    override val orderRepository = orderRepositoryMock
    override val itemRepository = itemRepositoryMock
    override val productRepository = productRepositoryMock
  }

  test("Find success orders by dates: '2022-01-01' and '2022-12-31 00:00'") {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val start = LocalDateTime.parse("2022-01-01 00:00", format)
    val end = LocalDateTime.parse("2022-12-31 00:00", format)

    val orders = OrderTemplate.orders
    val items = OrderTemplate.items
    val products = OrderTemplate.products

    when(orderRepositoryMock.findBy(start, end)).thenReturn(Future.successful(orders))
    orders.foreach(order => {
      val orderItems = items.filter(order.code === _.orderCode).toList
      val productCodes = orderItems.map(_.productCode).toSeq
      val itemProducts = products.filter(prod => productCodes.contains(prod.code)).toList
      when(itemRepositoryMock.findBy(order.code)).thenReturn(Future.successful(orderItems))
      when(productRepositoryMock.findBy(productCodes)).thenReturn(Future.successful(itemProducts))
    })

    val result = Await.result(orderService.findBy(start, end), 1000.seconds)

    assert(result.filter(_.period.equals(PeriodEnum.MONTHS_1_3)).last.total == 2)
    assert(result.filter(_.period.equals(PeriodEnum.MONTHS_4_6)).last.total == 1)
    assert(result.filter(_.period.equals(PeriodEnum.MONTHS_7_12)).last.total == 2)
    assert(result.filter(_.period.equals(PeriodEnum.MONTHS_OUTHERS)).last.total == 1)

    println("----------------")
    result.foreach(data =>{
      println(s"result: ${data.period} orders: ${data.total}")
    })
    println("----------------")
    assert(result.isEmpty === false)
  }

}
