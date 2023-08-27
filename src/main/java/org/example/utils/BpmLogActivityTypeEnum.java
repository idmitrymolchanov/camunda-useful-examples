package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BpmLogActivityTypeEnum {

  START("start"),
  COMPLETE("complete");

  private final String value;

}