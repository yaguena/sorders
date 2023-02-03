package com.gaguena.sorders.service

import java.time.LocalDateTime
import java.time.Period.between

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

import com.gaguena.sorders.data._
import com.gaguena.sorders.enums.PeriodEnum._
import com.gaguena.sorders.persistence.model.{ Item, Items, Order, Orders }
import com.gaguena.sorders.repository.{ ItemRepository, OrderRepository, ProductRepository }
import com.gaguena.sorders.enums.PeriodEnum

trait OrderServiceComponent {
  val orderService = new OrderService;
}

trait RepositoryComponent {
  val orderRepository = new OrderRepository
  val itemRepository = new ItemRepository
  val productRepository = new ProductRepository
}

class OrderService extends RepositoryComponent {

  def findByOnePeriod(period: PeriodEnum): Future[List[OrderPeriod]] = {
    val current = LocalDateTime.now
    period match {
      case MONTHS_1_3 => this.findBy(current.minusMonths(3), current)
      case MONTHS_4_6 => this.findBy(current.minusMonths(6), current)
      case MONTHS_7_12 => this.findBy(current.minusMonths(12), current)
      case MONTHS_OUTHERS => this.findBy(current)
    }
  }

 def findBy(start: LocalDateTime, end: LocalDateTime): Future[List[OrderPeriod]] = {
    for {
      orders <- this.getBy(start, end)
      items = orders.map(_.items)
      unit = println(s"items process $items")
      products = Option(items)
        .filter(!_.isEmpty)
        .map(_.map(_.map(_.products.map(monthMerge(end, _)))).map(_.reduce((a, b) => a ++ b)).reduce((a, b) => a ++ b))
        .orElse(Some(Seq.empty)).get //Muito feio
      months_1_3 = OrderPeriod(MONTHS_1_3, products.count(_.equals(MONTHS_1_3)))
      months_4_6 = OrderPeriod(MONTHS_4_6, products.count(_.equals(MONTHS_4_6)))
      months_7_12 = OrderPeriod(MONTHS_7_12, products.count(_.equals(MONTHS_7_12)))
      monthsOuthersTotal = products.length - (months_1_3.total + months_4_6.total + months_7_12.total)
      monthsOuthers = OrderPeriod(MONTHS_OUTHERS, monthsOuthersTotal)
      months = List(months_1_3, months_4_6, months_7_12, monthsOuthers)
    } yield months
  }

  private def findBy(date: LocalDateTime): Future[List[OrderPeriod]] = {
    for {
      orders <- this.getBy(date)
      items = orders.map(_.items)
      unit = println(s"items process $items")
      products = Option(items)
        .filter(!_.isEmpty)
        .map(_.map(_.map(_.products.map(monthMerge(date, _)))).map(_.reduce((a, b) => a ++ b)).reduce((a, b) => a ++ b))
        .orElse(Some(Seq.empty)).get //Muito feio
      months_1_3 = OrderPeriod(MONTHS_1_3, products.count(_.equals(MONTHS_1_3)))
      months_4_6 = OrderPeriod(MONTHS_4_6, products.count(_.equals(MONTHS_4_6)))
      months_7_12 = OrderPeriod(MONTHS_7_12, products.count(_.equals(MONTHS_7_12)))
      monthsOuthersTotal = products.length - (months_1_3.total + months_4_6.total + months_7_12.total)
      monthsOuthers = OrderPeriod(MONTHS_OUTHERS, monthsOuthersTotal)
      months = List(months_1_3, months_4_6, months_7_12, monthsOuthers)
    } yield months
  }

  def create(data: OrderData) = {
    def extractFuture(itemFuture: Future[Item]) = itemFuture.value
      .map(_ match { case Success(value) => ItemData.to(value) })
      .getOrElse(throw new RuntimeException)

    val order = orderRepository.save(Orders.to(data))
    data.items.map(Items.to(data.code, _)).map(itemRepository.save(_))
    order.map(OrderData.to(_, data.items))
  }

  def cancel(code: String) = ???

  private def getBy(start: LocalDateTime, end: LocalDateTime):Future[List[OrderData]]  = {
    for {
      orders <- orderRepository.findBy(start, end)
      ordersDatas = orders.map(createOrderItems(_))
      result <- Future.sequence(ordersDatas)
    } yield result
  }

  private def getBy(date: LocalDateTime):Future[List[OrderData]]  = {
    for {
      orders <- orderRepository.findBy(date)
      ordersDatas = orders.map(createOrderItems(_))
      result <- Future.sequence(ordersDatas)
    } yield result
  }

  private def createOrderItems(order: Order) = for {
    items <- itemRepository.findBy(order.code).map(_.toList)
    val productCodes = items.map(_.productCode)
    productsDatas <- productRepository.findBy(productCodes).map(_.map(ProductData.to(_)).toList)
    val itemsDatas = items.map(item => ItemData.to(item, productsDatas.filter(_.code == item.productCode))).toList
  } yield OrderData.to(order, itemsDatas)

  private def monthMerge(date: LocalDateTime, product: ProductData) = {
    val yearDiff = between(product.createdAt.toLocalDate, date.toLocalDate).getYears * 12
    between(product.createdAt.toLocalDate, date.toLocalDate).getMonths + yearDiff match {
      case n if n < 4 => MONTHS_1_3
      case n if n >= 4 && n < 7 => MONTHS_4_6
      case n if n >= 7 && n < 12 => MONTHS_7_12
      case _ => MONTHS_OUTHERS
    }
  }
}
