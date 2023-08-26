package org.example.bpmn.external_task_example;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.example.bpmn.BaseBpmDelegate;
import org.example.bpmn.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendErrorStatusDelegate")
public class SendErrorStatusDelegate extends BaseBpmDelegate {

  @Override
  public void perform(DelegateExecution execution) {
    var rejectReason = execution.getVariable(ProcessVariables.REJECT_REASON);
    log.info("businessKey: {}, reject reason: {}", execution, rejectReason.toString());
  }

}