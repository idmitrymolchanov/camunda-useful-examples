package org.example.bpmn.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProcessVariables {

  // event subprocess process
  public static final String ACTION_NUMBER = "actionNumber";
  public static final String EXAMPLE_ACTIONS_LIST = "exampleActionsList";

  // external task process
  public static final String EXAMPLE_INPUT_VARIABLE = "exampleInputVariable";
  public static final String ACTION_FROM_LIST = "actionFromList";
  public static final String IS_PROCESS_EXCEPTION = "isProcessException";
  public static final String REJECT_REASON = "rejectReason";

}