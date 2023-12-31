package org.example.bpmn.conditional_event_sub_example_pa;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.example.bpmn.BaseBpmDelegate;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PerformActionDelegate")
public class PerformActionDelegate extends BaseBpmDelegate {

  private static int actionCount = 0;

  static {
    actionCount++;
  }

  @Override
  public void perform(DelegateExecution execution) {
    log.info("perform action: {}", actionCount);
    execution.setVariable(ProcessVariables.ACTION_NUMBER, String.format("action-%d", actionCount));
  }

}