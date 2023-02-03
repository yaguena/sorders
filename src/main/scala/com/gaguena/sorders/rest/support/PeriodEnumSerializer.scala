package com.gaguena.sorders.rest.support

import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString
import com.gaguena.sorders.enums.PeriodEnum

object PeriodEnumSerializer extends CustomSerializer[PeriodEnum](format => (
  {
    case JString(d) => PeriodEnum.valueOf(d)
    case _ => null
  },
  {
    case x: PeriodEnum => JString(x.label)
  }
))
