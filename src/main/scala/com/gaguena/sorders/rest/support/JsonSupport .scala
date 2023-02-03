package com.gaguena.sorders.rest.support

import org.json4s.{ DefaultFormats, Formats }
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.ScalatraBase

trait JsonSupport extends RestSupport with JacksonJsonSupport {
  self: ScalatraBase =>

  protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal + LocalDateTimeSerializer + PeriodEnumSerializer

  val acceptmediaTypes: List[String]

  before() {
    if (acceptmediaTypes.find(request.getHeader("Accept").contains(_)).isEmpty) {
      halt(406, s"Content Accept error, versions: ${acceptmediaTypes}")
    }
    contentType = formats("json")
  }
}