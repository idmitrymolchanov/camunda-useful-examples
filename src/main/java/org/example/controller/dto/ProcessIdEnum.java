package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessIdEnum {

  CONDITIONAL_EVENT_SUB_EXAMPLE_PA("conditional-event-sub-example-pa"),
  EXTERNAL_TASK_EXAMPLE_PA("external-task-example-pa"),

  // all-elements-example-pa processes
  ALL_ELEMENTS_EXAMPLE_PA("all-elements-example-pa"),
  ALL_ELEMENTS_EXAMPLE_CALL_ACTIVITY_PA("all-elements-example-call-activity-pa"),
  ALL_ELEMENTS_EXAMPLE_EVENT_SUB_PA("all-elements-example-event-sub-pa");

  private final String value;

}