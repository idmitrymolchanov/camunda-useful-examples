package org.example.bpmn.external_task_example;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.example.bpmn.BaseBpmDelegate;
import org.example.model.ProcessVariables;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DefineActionListDelegate")
public class DefineActionListDelegate extends BaseBpmDelegate {

  @Override
  public void perform(DelegateExecution execution) {
    log.info("defining list of example actions");
    var exampleActionsList = List.of(
        "action-1",
        "action-2",
        "action-3"
    );

    execution.setVariable(ProcessVariables.EXAMPLE_ACTIONS_LIST, exampleActionsList);
  }

}