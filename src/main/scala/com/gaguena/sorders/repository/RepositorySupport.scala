package com.gaguena.sorders.repository

import slick.driver.MySQLDriver.api._
import scala.concurrent.Future
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import org.slf4j.LoggerFactory

trait DataBase {
  private val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("order.db")
  def db = dbConfig.db
  val logger = LoggerFactory.getLogger(getClass)
  import dbConfig.driver.api._
}

class Repository[T] extends DataBase {

  private def run[T](query: => DBIO[T]): Future[T] = {
    logger.info(s"Repository run $query")
    db.run[T](query)
  }


  def transactionally[T](query: => DBIO[T]): Future[T] = run(query.transactionally)
  
}
