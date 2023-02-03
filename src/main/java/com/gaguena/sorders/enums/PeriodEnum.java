package com.gaguena.sorders.enums;

import java.util.Arrays;

public enum PeriodEnum {
  MONTHS_1_3("1-3 months", "1-3"),
  MONTHS_4_6("4-6 months", "4-6"),
  MONTHS_7_12("7-12 months", "7-12"),
  MONTHS_OUTHERS("> 12 months", ">12");

  public final String label;
  public final String key;

  PeriodEnum(String label, String key) {
    this.label = label;
    this.key = key;
  }

  public static PeriodEnum getKey(String key) {
    return Arrays.asList(PeriodEnum.values()).stream()
      .filter(period -> period.key.equals(key))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}
