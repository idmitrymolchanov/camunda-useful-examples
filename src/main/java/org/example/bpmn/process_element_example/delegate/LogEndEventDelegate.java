package org.example.bpmn.process_element_example.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("LogEndEventDelegate")
public class LogEndEventDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    var endingReason = execution.getVariableLocal("endingReason").toString();
    log.info("businessKey: {}, reject reason: {}", execution.getBusinessKey(), endingReason);
  }

}