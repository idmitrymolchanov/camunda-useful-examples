package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessIdEnum {

  // TODO -> rename process's ids
  PROCESS_ELEMENT_EXAMPLE("process-element-example"), // all-elements-example-pa
  SUB_PROCESS_EXAMPLE("sub_process_example"), // all-elements-example-call-activity-pa
  EVENTED_SUB_PROCESS_EXAMPLE("evented_sub_process_example"), // all-elements-example-event-sub-pa
  EVENT_SUB_PROCESS_EXAMPLE("event-sub-process-example"), // conditional-event-sub-example-pa
  EXTERNAL_TASK_EXAMPLE("external-task-example"); // external-task-example-pa

  private final String value;

}