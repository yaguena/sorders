package com.gaguena.sorders.rest.support

import org.scalatra.UrlGeneratorSupport
import org.scalatra.FutureSupport
import org.scalatra.ScalatraServlet
import scala.concurrent.ExecutionContext
import akka.actor.ActorSystem


trait RestSupport extends ScalatraServlet
  with FutureSupport
  with UrlGeneratorSupport {

  protected implicit def executor: ExecutionContext = ActorSystem().dispatcher
}