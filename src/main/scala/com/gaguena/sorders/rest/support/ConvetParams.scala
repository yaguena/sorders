package com.gaguena.sorders.rest.support

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ConvetParams {

  implicit def toLocalDateTime(date: String) = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

}