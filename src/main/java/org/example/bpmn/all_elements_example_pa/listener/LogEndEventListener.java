package org.example.bpmn.all_elements_example_pa.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("LogEndEventListener")
public class LogEndEventListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) {
    var endingReason = execution.getVariableLocal("endingReason").toString();
    log.info("businessKey: {}, reject reason: {}", execution.getBusinessKey(), endingReason);
  }

}