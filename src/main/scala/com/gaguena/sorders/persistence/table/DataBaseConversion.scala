package com.gaguena.sorders.persistence.table

import java.sql.Timestamp
import java.time.{ LocalDate, LocalDateTime }

import scala.reflect.ClassTag

import slick.driver.MySQLDriver.api._

object DataBaseConversion {

  implicit def timestampLocalDateTime = MappedColumnType.base[LocalDateTime, Timestamp](
    dt => Timestamp.valueOf(dt),
    ts => ts.toLocalDateTime)

  implicit def enumString[T <: Enum[T]](implicit classTag: ClassTag[T]) =
    MappedColumnType.base[T, String](
      enum => enum.name,
      stringValue => java.lang.Enum.valueOf(classTag.runtimeClass.asInstanceOf[Class[T]], stringValue))

  implicit val listString = MappedColumnType.base[List[String], String](
    list => list.mkString(","),
    string => string.split(",").toList)

  implicit def dateLocalDate = MappedColumnType.base[LocalDate, java.sql.Date](
    localDate => java.sql.Date.valueOf(localDate),
    date => date.toLocalDate)
}
