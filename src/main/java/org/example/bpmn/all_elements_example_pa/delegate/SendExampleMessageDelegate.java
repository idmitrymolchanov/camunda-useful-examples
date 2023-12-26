package org.example.bpmn.all_elements_example_pa.delegate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SendExampleMessageDelegate")
public class SendExampleMessageDelegate implements JavaDelegate {

  @Override
  @SneakyThrows
  public void execute(DelegateExecution execution) {
    var businessKey = execution.getBusinessKey();
    var taskName = execution.getEventName();
    execution.setVariable(ProcessVariables.ONGOING_PROCESS, true);

    log.info("businessKey: {}, preparing request for task: {}", businessKey, taskName);
  }

}