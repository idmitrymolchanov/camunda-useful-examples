package org.example.bpmn.process_element_example.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ReceiveExampleMessageListener")
public class ReceiveExampleMessageListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) {
    var businessKey = execution.getBusinessKey();
    log.info("businessKey: {}, message received", businessKey);
    var createError = (Boolean) execution.getVariable(ProcessVariables.CREATE_ERROR);
    log.info("businessKey: {}, error in process: {}", businessKey, createError);

    if(Boolean.TRUE.equals(createError)) {
      throw new BpmnError("PROCESS_ERROR", "got error variable = true");
    }
  }

}