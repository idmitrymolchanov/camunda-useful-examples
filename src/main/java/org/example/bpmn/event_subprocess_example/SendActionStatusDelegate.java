package org.example.bpmn.event_subprocess_example;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.example.bpmn.BaseBpmDelegate;
import org.example.bpmn.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendActionStatusDelegate")
public class SendActionStatusDelegate extends BaseBpmDelegate {

  @Override
  public void perform(DelegateExecution execution) {
    var actionNumber = execution.getVariable(ProcessVariables.ACTION_NUMBER).toString();
    log.info("sending status for action: {}", actionNumber);
  }

}