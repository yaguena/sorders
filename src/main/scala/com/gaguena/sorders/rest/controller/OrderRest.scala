package com.gaguena.sorders.rest.controller

import java.time.LocalDateTime

import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

import org.scalatra.{ AsyncResult, BadRequest, Ok }

import com.gaguena.sorders.data.OrderData
import com.gaguena.sorders.rest.support.{ JsonSupport, RestSupport }
import com.gaguena.sorders.rest.support.ConvetParams.toLocalDateTime
import com.gaguena.sorders.service.OrderServiceComponent
import com.gaguena.sorders.enums.PeriodEnum

class OrderRest extends RestSupport with JsonSupport with OrderServiceComponent {

  lazy val OrderRestType = "application/vnd.gaguena.autor.v1+json"
  lazy val badRequest = BadRequest("Invalid request body")
  override val acceptmediaTypes = List(OrderRestType)

    get("/") {
     new AsyncResult {
       val is = Try (Option(params("start")), Option(params("end"))) match {
        case Success((Some(start), Some(end))) => orderService.findBy(start, end).map(Ok(_))
        case _ => Try (Option(params("range"))) match {
          case Success(Some(range)) =>  orderService.findByOnePeriod(PeriodEnum.getKey(range)).map(Ok(_))
          case _ => Future.successful(badRequest)
        }
      }
    }
  }

  post("/") {
    new AsyncResult {
      val body = request.body
      val is = Try(parse(body).extract[OrderData]) match {
          case Success(order) => {
            orderService.create(order).map(Ok(_))
          }
          case Failure(_) => {
            Future.successful(badRequest)
          }
      }
    }
  }

}

