package com.gaguena.sorders.rest.controller

import com.gaguena.sorders.rest.support._
import scala.util.Try
import com.gaguena.sorders.data.ProductData
import scala.util.Success
import com.gaguena.sorders.service.ProductServiceComponent
import org.scalatra.Ok
import scala.util.Failure
import org.scalatra.BadRequest
import scala.concurrent.Future
import org.scalatra.AsyncResult

class ProductRest  extends RestSupport with JsonSupport with ProductServiceComponent {

  val OrderRestType = "application/vnd.gaguena.autor.v1+json"

  override val acceptmediaTypes = List(OrderRestType)

  post("/") {
    new AsyncResult {
      val body = request.body
      val is = Try(parse(body).extract[ProductData]) match {
          case Success(product) => productService.create(product).map(Ok(_))
          case Failure(_) => {
            Future.successful(BadRequest("Corpo da requisição(parametros) invalidos"))
          }
      }
    }
  }
}