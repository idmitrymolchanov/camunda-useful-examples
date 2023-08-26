package org.example.bpmn.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BpmLogActivityTypeEnum {

  START("start"),
  COMPLETE("complete");

  private final String value;

}